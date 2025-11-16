const API_BASE = "http://localhost:8081/api/usuarios";

//evitar que sucedan errores con el login
async function validarSesion() {
    const user = JSON.parse(localStorage.getItem("usuario") || "{}");

    // No hay sesión guardada
    if (!user.idUsuario) {
        localStorage.clear();
        window.location.href = "login.html";
        return;
    }

    // Intentar validar el usuario contra el backend
    try {
        const resp = await fetch(`${API_BASE}/${user.idUsuario}`);

        if (!resp.ok) {
            console.warn("Sesión inválida detectada. Limpiando...");
            localStorage.clear();
            window.location.href = "login.html";
            return;
        }

        const usuario = await resp.json();


    } catch (error) {
        // Backend apagado, sesión insegura → limpiar
        console.warn("Backend no disponible, limpiando sesión...");
        localStorage.clear();
    }
}
