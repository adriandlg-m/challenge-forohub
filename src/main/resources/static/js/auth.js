let token = "";

function login() {
    const email = document.getElementById('user-email').value;
    const pass = document.getElementById('user-pass').value;

    if (!email || !pass) {
        showToast("Por favor, completa los campos", true);
        return;
    }

    fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ login: email, clave: pass })
    })
        .then(res => {
            if (!res.ok) throw new Error("Credenciales inválidas");
            return res.json();
        })
        .then(data => {

            if(data.jwTtoken) {
                localStorage.setItem('foro_token', data.jwTtoken);

                const nombreParaGuardar = data.nombre || email.split('@')[0];
                localStorage.setItem('foro_user', nombreParaGuardar);

                token = data.jwTtoken;
                document.getElementById('display-name').innerText = nombreParaGuardar;

                document.getElementById('login-section').style.display = 'none';
                document.getElementById('dashboard-ui').style.display = 'block';
                document.getElementById('layout-main').style.display = 'grid';

                loadTopics();
                showToast("¡Bienvenido, " + nombreParaGuardar + "!");
            }
        })
        .catch(err => {
            showToast("Error: Usuario o contraseña incorrectos", true);
            console.error(err);
        });
}

function registrarUsuario() {
    const nombre = document.getElementById('reg-nombre').value;
    const email = document.getElementById('reg-email').value;
    const pass = document.getElementById('reg-pass').value;

    fetch('http://localhost:8080/registro', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            login: email,
            clave: pass,
            nombre: nombre // Enviamos el nombre al backend
        })
    })
        .then(response => {
            if (response.ok) {
                showToast(`¡Cuenta creada para ${nombre}!`);
                toggleAuth(false);
            }
        });
}

function toggleAuth(showRegister) {
    document.getElementById('login-form').style.display = showRegister ? 'none' : 'block';
    document.getElementById('register-form').style.display = showRegister ? 'block' : 'none';

    // Opcional: Limpiar campos al cambiar
    if(showRegister) {
        document.getElementById('reg-email').value = "";
        document.getElementById('reg-pass').value = "";
    }
}

function logout() {
    localStorage.removeItem('foro_token');
    localStorage.removeItem('foro_user');

    token = "";

    showToast("Sesión cerrada correctamente");

    setTimeout(() => {
        location.reload();
    }, 500); // Damos medio segundo para que se vea el toast
}

function checkSession() {
    const savedToken = localStorage.getItem('foro_token');
    const savedUser = localStorage.getItem('foro_user');

    if (savedToken) {
        token = savedToken;
        document.getElementById('login-section').style.display = 'none';
        document.getElementById('dashboard-ui').style.display = 'block';
        document.getElementById('layout-main').style.display = 'grid';
        document.getElementById('display-name').innerText = savedUser;
        loadTopics();
    }
}