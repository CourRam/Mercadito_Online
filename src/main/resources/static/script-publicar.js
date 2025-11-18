// Cargar categorías desde backend
async function cargarCategorias() {
    const resp = await fetch("http://localhost:8081/api/categorias/listarDTO");
    const categorias = await resp.json();

    const sel = document.getElementById("categoria");
    sel.innerHTML = "";

    categorias.forEach(cat => {
        const op = document.createElement("option");
        op.value = cat.idCategoria;
        op.textContent = cat.nombre;
        sel.appendChild(op);
    });
}



// Enviar formulario con imagen
const form = document.getElementById("formPublicar");
form.addEventListener("submit", async (e) => {
    e.preventDefault();

    let usuario = localStorage.getItem("usuario");

    try {
        usuario = JSON.parse(usuario);
        if (typeof usuario === "string") {
            usuario = JSON.parse(usuario);
        }
    } catch (e) {
        return alert("Error al leer usuario. Vuelve a iniciar sesión.");
    }
    
    if (!usuario.idUsuario) return alert("Sesión expirada.");

    if (document.getElementById("stock").value<1) return alert("No puedes publicar Productos sin stock");

    if(document.getElementById("precio").value<=0) return alert("Favor de poner un precio");

    const data = new FormData();
    data.append("nombre", document.getElementById("nombre").value);
    data.append("descripcion", document.getElementById("descripcion").value);
    data.append("precio", document.getElementById("precio").value);
    data.append("stock", document.getElementById("stock").value);
    data.append("idUsuario", usuario.idUsuario);
    data.append("idCategoria", document.getElementById("categoria").value);
    data.append("imagen", document.getElementById("imagen").files[0]);

    const resp = await fetch("http://localhost:8081/api/productos/crear", {
        method: "POST",
        body: data
    });

    if (!resp.ok) {
        alert(await resp.text());
        return;
    }

    alert("Producto publicado con éxito");
    window.location.href = "index.html";
});