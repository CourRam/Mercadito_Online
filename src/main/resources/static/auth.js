const API_BASE = "http://localhost:8081/api/usuarios";

//evitar que sucedan errores con el login
async function validarSesion() {
    const userId = localStorage.getItem("usuarioId");

    // No hay sesión guardada
    if (!userId) {
        mostrarBotonesNoLogeado();
        return;
    }

    // Intentar validar el usuario contra el backend
    try {
        const resp = await fetch(`${API_BASE}/${userId}`);

        if (!resp.ok) {
            console.warn("Sesión inválida detectada. Limpiando...");
            localStorage.clear();
            mostrarBotonesNoLogeado();
            return;
        }

        const usuario = await resp.json();


    } catch (error) {
        // Backend apagado, sesión insegura → limpiar
        console.warn("Backend no disponible, limpiando sesión...");
        localStorage.clear();
    }
}
