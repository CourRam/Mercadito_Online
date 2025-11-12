const API_URL = "http://localhost:8081/api/productos"; 

// Cargar productos al inicio
document.addEventListener("DOMContentLoaded", cargarProductos);

async function cargarProductos() {
    const contenedor = document.getElementById("productos-container");

    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error("Error al obtener productos");

        const productos = await response.json();

        if (productos.length === 0) {
            contenedor.innerHTML = "<p>No hay productos disponibles.</p>";
            return;
        }

        contenedor.innerHTML = ""; // Limpia el contenido previo

        productos.forEach(prod => {
            const card = document.createElement("div");
            card.className = "product-card";
            card.innerHTML = `
                <img src="${prod.imagen || 'https://via.placeholder.com/250x180?text=Sin+Imagen'}" alt="${prod.nombre}">
                <div class="info">
                    <h3>${prod.nombre}</h3>
                    <p>${prod.descripcion || 'Sin descripción'}</p>
                    <p class="price">$${prod.precio.toFixed(2)}</p>
                    <button onclick="agregarAlCarrito(${prod.idProducto})">Agregar al carrito</button>
                </div>
            `;
            contenedor.appendChild(card);
        });

    } catch (error) {
        contenedor.innerHTML = `<p>Error al cargar productos: ${error.message}</p>`;
    }
}

// Simula agregar al carrito (por ahora solo localStorage)
function agregarAlCarrito(idProducto) {
    let carrito = JSON.parse(localStorage.getItem("carrito")) || [];
    carrito.push(idProducto);
    localStorage.setItem("carrito", JSON.stringify(carrito));
    alert("Producto agregado al carrito 🛒");
}
