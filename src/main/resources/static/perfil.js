const user = JSON.parse(localStorage.getItem("usuario") || "{}");
const ID_USUARIO=user.idUsuario;
// ------------------------ CARGAR DATOS DEL USUARIO ------------------------
async function cargarUsuario() {

    const res = await fetch(`http://localhost:8081/api/usuarios/${ID_USUARIO}`);
    if(!res.ok){
        alert("error al cargar usuario");
    }
    const usuario = await res.json();

    document.getElementById("usuario-info").innerHTML = `
        <p><strong>Nombre:</strong> ${usuario.nombre}</p>
        <p><strong>Correo:</strong> ${usuario.correo}</p>
        <p><strong>Teléfono:</strong> ${usuario.telefono}</p>
    `;
}

// ------------------------ HISTORIAL DE COMPRAS ------------------------
async function cargarHistorialViejo() {
    const res = await fetch(`http://localhost:8081/api/historial-ventas/usuario/${ID_USUARIO}`);
    if(!res.ok){
        alert("error al cargar historial");
    }
    const historial = await res.json();

    

    const tbody = document.getElementById("historial-body");
    tbody.innerHTML = "";

    historial.forEach(v => {
        const carrito=obtenerCarritoPorId(v.idCarrito);
        tbody.innerHTML += `
            <tr>
                <td>${v.idVenta}</td>
                <td>${v.fecha}</td>
                <td>$${v.total}</td>
            </tr>
        `;
    });
}

//Cargar Historial nuevo
async function cargarHistorial() {
  const res = await fetch(`http://localhost:8081/api/historial-ventas/usuario/${ID_USUARIO}`);
  if (!res.ok) {
    alert("error al cargar historial");
    return;
  }

  const historial = await res.json();

  const tbody = document.getElementById("historial-body");
  tbody.innerHTML = "";

  // Usamos for...of porque necesitamos await dentro del loop
  for (const v of historial) {
    // Esperamos el carrito completo
    const carrito = await obtenerCarritoPorId(v.idCarrito);

    // Construimos la lista de productos
    let productosHTML = "<ul>";
    carrito.detalles.forEach(d => {
      productosHTML += `<li>${d.nombreProducto} | cantidad: ${d.cantidad}</li>`;
    });
    productosHTML += "</ul>";

    // Agregamos la fila con la lista en la última columna
    tbody.innerHTML += `
      <tr>
        <td>${v.idVenta}</td>
        <td>${v.fecha}</td>
        <td>$${v.total}</td>
        <td>${productosHTML}</td>
      </tr>
    `;
  }
}


// ------------------------ PRODUCTOS ACTIVOS ------------------------
async function cargarProductosActivos() {
    const res = await fetch(`http://localhost:8081/api/productos/activos/${ID_USUARIO}`);
    const productos = await res.json();

    
    const contenedor = document.getElementById("productos-activos");
    contenedor.innerHTML = "";

    productos.forEach(p => {
        contenedor.innerHTML += `
            <div class="card">
                <h4>${p.nombre}</h4>
                <p>${p.descripcion}</p>
                <p><strong>$${p.precio}</strong></p>
                <p><small>Stock Disponible: ${p.stock}</small></p>
            </div>
        `;
    });
}

// ------------------------ PRODUCTOS VENDIDOS ------------------------
async function cargarProductosVendidos() {
    const res = await fetch(`http://localhost:8081/api/productos/vendidos/${ID_USUARIO}`);
    const productos = await res.json();

    const contenedor = document.getElementById("productos-vendidos");
    contenedor.innerHTML = "";

    productos.forEach(p => {
        contenedor.innerHTML += `
            <div class="card">
                <h4>${p.nombre}</h4>
                <p>${p.descripcion}</p>
                <p><strong>Stock vendido: ${p.stockVendido}<strong/><p/>
                <p><strong>Precio por unidad: $${p.precio}</strong></p>
                <p><strong>Ganancia Total: $${p.gananciaTotal}</strong></p>
            </div>
        `;
    });
}

// ------------------------ INICIALIZAR PÁGINA ------------------------
async function iniciarPerfil() {
    await cargarUsuario();
    await cargarHistorial();
    await cargarProductosActivos();
    await cargarProductosVendidos();
}

async function obtenerCarritoPorId(idCarrito) {
  const res = await fetch(`http://localhost:8081/api/carritos/${idCarrito}`, {
    method: "GET"
  });

  if (!res.ok) {
    throw new Error("Error al obtener el carrito");
  }

  const carrito = await res.json(); // porque el endpoint devuelve un DTO en JSON
  console.log(carrito);
  return carrito;
}



document.addEventListener("DOMContentLoaded", iniciarPerfil);
