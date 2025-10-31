// --- Productos simulados ---
const productos = [
  {
    id: 1,
    nombre: "Laptop HP Pavilion",
    descripcion: "Laptop con Ryzen 5 y 16GB RAM, ideal para estudiantes.",
    precio: 9500,
    imagen: "https://via.placeholder.com/400x250?text=Laptop+HP"
  },
  {
    id: 2,
    nombre: "Teléfono Samsung A54",
    descripcion: "Pantalla AMOLED de 6.6” y cámara de 50MP.",
    precio: 7800,
    imagen: "https://via.placeholder.com/400x250?text=Samsung+A54"
  },
  {
    id: 3,
    nombre: "Audífonos Bluetooth JBL",
    descripcion: "Sonido potente y batería de larga duración.",
    precio: 1200,
    imagen: "https://via.placeholder.com/400x250?text=JBL+Bluetooth"
  },
  {
    id: 4,
    nombre: "Silla Gamer Redragon",
    descripcion: "Ergonómica, ajustable y con cojines incluidos.",
    precio: 3500,
    imagen: "https://via.placeholder.com/400x250?text=Silla+Gamer"
  }
];

let carrito = [];

const lista = document.getElementById("listaProductos");
const contadorCarrito = document.getElementById("contadorCarrito");

// --- Mostrar productos ---
function mostrarProductos() {
  lista.innerHTML = "";
  productos.forEach(prod => {
    const card = document.createElement("div");
    card.className = "card";
    card.innerHTML = `
      <img src="${prod.imagen}" alt="${prod.nombre}">
      <div class="card-content">
        <h3>${prod.nombre}</h3>
        <p>${prod.descripcion}</p>
        <p class="precio">$${prod.precio}</p>
        <button onclick="agregarAlCarrito(${prod.id})">Agregar al carrito</button>
      </div>
    `;
    lista.appendChild(card);
  });
}

// --- Agregar al carrito ---
function agregarAlCarrito(id) {
  const producto = productos.find(p => p.id === id);
  carrito.push(producto);
  contadorCarrito.textContent = carrito.length;
  alert(`"${producto.nombre}" se agregó al carrito 🛒`);
}

mostrarProductos();
