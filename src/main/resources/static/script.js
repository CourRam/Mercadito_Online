const API_URL_PRODUCTOS = "http://localhost:8081/api/productos/listarDTO";
const API_URL_CATEGORIAS = "http://localhost:8081/api/categorias/listarDTO";

let productosGlobal = [];
let categoriasGlobal = [];
let detallesCache = null;
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

        //ponemos el detallesCache en nulo para que se refreque correctamente
        detallesCache = null;
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

// Mostrar productos(VIEJO, NO TIENE ASYNC)
function mostrarProductosViejo(productos) {
    const contenedor = document.getElementById("productos-container");

    if (productos.length === 0) {
        contenedor.innerHTML = "<p>No hay productos disponibles.</p>";
        return;
    }

    contenedor.innerHTML = "";

    productos.forEach(prod => {
        let cantidadCarrito=contarStockProducto(prod.idProducto);
        if((prod.stock-cantidadCarrito)>0){
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
        }
        
    });
}
// Mostrar productos
async function mostrarProductos(productos) {
    const contenedor = document.getElementById("productos-container");
    contenedor.innerHTML = "";

    const cantidades = await Promise.all(
        productos.map(p => contarStockProducto(p.idProducto))
    );

    productos.forEach((prod, index) => {
        const cantidadCarrito = cantidades[index];

        if (prod.stock - cantidadCarrito > 0) {
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
        }
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
//Para tener los detalles del carrito 
async function obtenerDetallesCarrito() {
    if (detallesCache) return detallesCache;

    const user = JSON.parse(localStorage.getItem("usuario"));
    if (!user) return [];

    const respCarrito = await fetch(`http://localhost:8081/api/carritos/carrito-actual?idUsuario=${user.idUsuario}`);
    if (!respCarrito.ok) return [];

    const carrito = await respCarrito.json();

    const respDetalle = await fetch(`http://localhost:8081/api/detalle-carrito/${carrito.idCarrito}`);
    if (!respDetalle.ok) return [];

    detallesCache = await respDetalle.json();
    return detallesCache;
}

async function contarStockProducto(idProducto) {
    const detalles = await obtenerDetallesCarrito();
    return detalles
        .filter(d => d.producto && d.producto.idProducto === idProducto)
        .reduce((acc, d) => acc + d.cantidad, 0);
}

async function agregarAlCarrito(idProducto) {
    try {
        const user = JSON.parse(localStorage.getItem("usuario"));
        if (!user || !user.idUsuario) {
            alert("Debes iniciar sesión para agregar productos.");
            return;
        }

        console.log("Solicitando carrito actual...");

        
        const respCarrito = await fetch(`http://localhost:8081/api/carritos/carrito-actual?idUsuario=${user.idUsuario}`, {
            method: "POST"
        });

        if (!respCarrito.ok) throw new Error("No se pudo obtener el carrito actual");

        const carrito = await respCarrito.json();
        console.log("Carrito obtenido:", carrito);

        console.log("Carrito obtenido:", carrito);
        if (!carrito || !carrito.idCarrito) {
            alert("El backend no devolvió un carrito válido");
            return;
        }
        
        console.log("Agregando producto al carrito...");

        const respDetalle = await fetch(`http://localhost:8081/api/detalle-carrito/${carrito.idCarrito}/agregar?idProducto=${idProducto}`, {
            method: "POST"
        });

        //if (!respDetalle.ok) throw new Error("Error al agregar producto");

        if (!respDetalle.ok) {
            const txt = await respDetalle.text();
            throw new Error("Error al agregar producto: " + txt);
        }


        console.log("Producto agregado exitosamente");

        alert("Producto agregado al carrito 🛒");

        location.reload();

    } catch (error) {
        console.error("Error al agregar al carrito:", error);
        alert("No se pudo agregar al carrito: " + error.message);
    }
}






// Iniciar sesion
async function login() {
    const correo = document.getElementById("correo").value;
    const password = document.getElementById("password").value;

    const resp = await fetch("http://localhost:8081/api/usuarios/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ correo, password })
    });

    if (!resp.ok) {
        alert("Correo o contraseña incorrectos");
        return;
    }

    const user = await resp.json();

    localStorage.setItem("usuario", JSON.stringify(user));
    window.location.href = "index.html";
}

//registrar nuevo usuario
async function registrar() {
    const nombre = document.getElementById("nombre").value;
    const correo = document.getElementById("correo").value;
    const password = document.getElementById("password").value;
    const telefono = document.getElementById("telefono").value;
    const direccion = document.getElementById("direccion").value;



    const resp = await fetch("http://localhost:8081/api/usuarios/crearFull", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre, correo, password,telefono,direccion })
    });

    if (!resp.ok) {
        alert(await resp.text());
        return;
    }
    
    alert("Cuenta creada. Ahora inicia sesión.");
    window.location.href = "login.html";
}

//Cerrar sesion
function logout() {
    alert("Cerrando sesión...");
    localStorage.removeItem("usuario");
    window.location.href = "login.html";
}