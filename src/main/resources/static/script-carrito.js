const API_CARRITO = "http://localhost:8081/api/carritos/carrito-actual";
const API_DETALLE = "http://localhost:8081/api/detalle-carrito";

let carrito = null;
const usuario = JSON.parse(localStorage.getItem("usuario") || "{}");

if (!usuario.idUsuario) {
  alert("No hay sesión iniciada.");
  window.location.href = "index.html";
}


//   Cargar carrito al iniciar
async function cargarCarrito() {
  try {
    const res = await fetch(`${API_CARRITO}?idUsuario=${usuario.idUsuario}`, {
      method: "POST",
    });
    
    if (!res.ok) throw new Error("Error al obtener el carrito");
    carrito = await res.json();
    renderCarrito();
  } catch (error) {
    alert("No se pudo cargar el carrito: " + error.message);
  }
}


//     Mostrar productos
function renderCarrito() {
  const cont = document.getElementById("items");
  cont.innerHTML = "";

  carrito.detalles.forEach((det) => {
    cont.innerHTML += `
                <div class="item">
                    <div>
                        <div class="name">${
                          det.nombreProducto ?? "Producto"
                        }</div>
                        <small>Precio: $${det.subtotal / det.cantidad}</small>
                    </div>

                    <div class="controls">
                        <button class="btn-sub" onclick="cambiarCantidad(${
                          det.idProducto
                        }, ${det.cantidad - 1})">-</button>
                        <span>${det.cantidad}</span>
                        <button class="btn-add" onclick="cambiarCantidad(${
                          det.idProducto
                        }, ${det.cantidad + 1})">+</button>
                        <button class="btn-delete" onclick="eliminar(${
                          det.idProducto
                        })">🗑</button>
                    </div>
                </div>
            `;
  });

  document.getElementById("total").innerText = "Total: $" + carrito.total;
}


//   Cambiar cantidad (+ / -)
async function cambiarCantidad(idProducto, nuevaCantidad) {
  if (nuevaCantidad <= 0) return eliminar(idProducto);

  await fetch(
    `${API_DETALLE}/${carrito.idCarrito}/actualizar?idProducto=${idProducto}&nuevaCantidad=${nuevaCantidad}`,
    {
      method: "PUT",
    }
  );

  cargarCarrito();
}


//   Eliminar producto
async function eliminar(idProducto) {
  await fetch(`${API_DETALLE}/${carrito.idCarrito}/eliminar?idProducto=${idProducto}`,
    {
      method: "DELETE",
    }
  );

  cargarCarrito();
}


//   Finalizar compra
async function finalizarCompra() {
  alert("Compra finalizada (pendiente implementar en backend)");
}


//   Cancelar compra
async function cancelarCompra() {
  await fetch(`${API_DETALLE}/${carrito.idCarrito}/vaciar`, {
    method: "DELETE",
  });

  alert("Carrito cancelado.");
  cargarCarrito();
}

cargarCarrito();
