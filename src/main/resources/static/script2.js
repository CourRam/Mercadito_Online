const API_URL_PRODUCTOS = "http://localhost:8081/api/productos/listarDTO";
const API_URL_CATEGORIAS = "http://localhost:8081/api/categorias/listarDTO";

let productosGlobal = [];
let categoriasGlobal = [];

// Cargar productos y categorías al inicio
document.addEventListener("DOMContentLoaded", async () => {
    await Promise.all([cargarCategorias(), cargarProductos()]);
});

//  Cargar productos desde el backend
async function cargarProductos() {
    const contenedor = document.getElementById("productos-container");

    try {
        const response = await fetch(API_URL_PRODUCTOS);
        if (!response.ok) throw new Error("Error al obtener productos");

        const productos = await response.json();
        productosGlobal = productos;

        mostrarProductos(productos);
    } catch (error) {
        contenedor.innerHTML = `<p>Error al cargar productos: ${error.message}</p>`;
    }
}

// Cargar categorías dinámicamente
async function cargarCategorias() {
    const select = document.getElementById("select-categoria");

    try {
        const response = await fetch(API_URL_CATEGORIAS);
        if (!response.ok) throw new Error("Error al obtener categorías");

        categoriasGlobal = await response.json();

        categoriasGlobal.forEach(cat => {
            const option = document.createElement("option");
            option.value = cat.idCategoria;
            option.textContent = cat.nombre;
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Error al cargar categorías:", error);
    }
}

// Mostrar productos
function mostrarProductos(productos) {
    const contenedor = document.getElementById("productos-container");

    if (productos.length === 0) {
        contenedor.innerHTML = "<p>No hay productos disponibles.</p>";
        return;
    }

    contenedor.innerHTML = "";

    productos.forEach(prod => {
        const card = document.createElement("div");
        card.className = "product-card";
        card.innerHTML = `
            <img src="${prod.imagenUrl || 'https://via.placeholder.com/250x180?text=Sin+Imagen'}" alt="${prod.nombre}">
            <div class="info">
                <h3>${prod.nombre}</h3>
                <p>${prod.descripcion || 'Sin descripción'}</p>
                <p class="price">$${prod.precio.toFixed(2)}</p>
                <button onclick="agregarAlCarrito(${prod.idProducto})">Agregar al carrito</button>
            </div>
        `;
        contenedor.appendChild(card);
    });
}

//  Filtrar productos
function filtrarProductos() {
    const texto = document.getElementById("input-busqueda").value.toLowerCase();
    const idCategoria = document.getElementById("select-categoria").value;
    const precioMin = parseFloat(document.getElementById("precio-min").value) || 0;
    const precioMax = parseFloat(document.getElementById("precio-max").value) || Infinity;

    const filtrados = productosGlobal.filter(prod => {
        const coincideTexto =
            prod.nombre.toLowerCase().includes(texto) ||
            (prod.descripcion && prod.descripcion.toLowerCase().includes(texto));

        const coincideCategoria =
            idCategoria === "" || prod.categoriaId === parseInt(idCategoria);

        const coincidePrecio =
            prod.precio >= precioMin && prod.precio <= precioMax;

        return coincideTexto && coincideCategoria && coincidePrecio;
    });

    mostrarProductos(filtrados);
}

//  Agregar producto al carrito
function agregarAlCarrito(idProducto) {
    let carrito = JSON.parse(localStorage.getItem("carrito")) || [];
    carrito.push(idProducto);
    localStorage.setItem("carrito", JSON.stringify(carrito));
    alert("Producto agregado al carrito 🛒");
}