function loadTopics() {
    const activeToken = token || localStorage.getItem('foro_token');
    const usuarioLogueado = (localStorage.getItem('foro_user') || "").toLowerCase().trim();

    fetch('http://localhost:8080/topicos', {
        headers: { 'Authorization': 'Bearer ' + activeToken }
    })
        .then(res => res.json())
        .then(data => {
            const container = document.getElementById('topics-list');
            const topics = data.content || data;
            container.innerHTML = "";

            topics.forEach(t => {

                const nombreAutor = t.autor || "Anónimo";
                const nombreCurso = t.curso || "Sin curso";
                const esDueño = (usuarioLogueado === nombreAutor.toLowerCase().trim());

                let bCls = "badge-default";
                const c = nombreCurso.toLowerCase();
                if(c.includes("java")) bCls = "badge-java";
                else if(c.includes("spring")) bCls = "badge-spring";
                else if(c.includes("mysql")) bCls = "badge-mysql";

                const card = document.createElement('div');
                card.className = 'topic-card';
                card.innerHTML = `
                <div class="card-header">
                    <div style="font-weight:700; font-size:0.95rem;">${t.titulo}</div>
                    <div class="card-actions">
                        ${esDueño ? `
                            <button class="btn-mini" onclick="openModal(${t.id}, '${t.titulo.replace(/'/g, "\\'")}', \`${t.mensaje}\`, '${nombreCurso}')">✏️</button>
                            <button class="btn-mini" style="color:var(--danger)" onclick="deleteTopic(${t.id})">🗑️</button>
                        ` : ''}
                    </div>
                </div>
                <div style="display: flex; justify-content: space-between; font-size:0.75rem; color:var(--text-muted); margin-bottom: 8px;">
                    <span><b>${nombreAutor}</b></span>
                    <span>• ${formatRelativeTime(t.fechaCreacion)}</span>
                </div>
                <p style="font-size:0.85rem;">${t.mensaje}</p>
                <span class="badge ${bCls}">${nombreCurso}</span>
            `;
                container.appendChild(card);
            });
        })
        .catch(err => console.error("Error cargando tópicos:", err));
}

function postTopic() {
    const payload = {
        titulo: document.getElementById('new-title').value,
        mensaje: document.getElementById('new-msg').value,
        curso: document.getElementById('new-course').value
        // ELIMINAMOS EL CAMPO AUTOR DE AQUÍ
    };

    if (!payload.titulo || !payload.mensaje) { showToast("Título y mensaje requeridos", true); return; }

    fetch('http://localhost:8080/topicos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        },
        body: JSON.stringify(payload)
    }).then(async res => {
        if(res.ok) {
            loadTopics();
            document.getElementById('new-title').value = "";
            document.getElementById('new-msg').value = "";
            showToast("¡Tópico publicado!");
        }else {
            const errorTexto = await res.text();
            showToast(errorTexto, true);
        }
    });
}

function openModal(id, titulo, mensaje, curso) {
    document.getElementById('edit-id').value = id;
    document.getElementById('edit-title').value = titulo;
    document.getElementById('edit-msg').value = mensaje;
    document.getElementById('edit-course').value = curso;
    document.getElementById('modal-edit').style.display = 'flex';
}

function closeModal() {
    document.getElementById('modal-edit').style.display = 'none';
}

function saveEdit() {
    const id = document.getElementById('edit-id').value;
    const payload = {
        titulo: document.getElementById('edit-title').value,
        mensaje: document.getElementById('edit-msg').value,
        curso: document.getElementById('edit-course').value
    };

    if (!payload.titulo || !payload.mensaje) { showToast("Título y mensaje requeridos", true); return; }

    fetch(`http://localhost:8080/topicos/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + token },
        body: JSON.stringify(payload)
    }).then(async res => {
        if(res.ok) { closeModal(); loadTopics(); showToast("Tópico actualizado"); }
        else { const err = await res.text(); showToast(err || "Error al actualizar", true); }
    }).catch(() => showToast("Error de conexión", true));
}

function deleteTopic(id) {
    if(!confirm("¿Estás segura de eliminar este tópico?")) return;
    fetch(`http://localhost:8080/topicos/${id}`, {
        method: 'DELETE',
        headers: { 'Authorization': 'Bearer ' + token }
    }).then(res => {
        if(res.ok) { loadTopics(); showToast("Tópico eliminado"); }
        else { showToast("No se pudo eliminar", true); }
    }).catch(() => showToast("Error de conexión", true));
}