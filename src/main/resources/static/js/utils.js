function showToast(msg, isError = false) {
    const t = document.getElementById('toast');
    t.innerText = msg;
    t.style.borderColor = isError ? 'var(--danger)' : 'var(--accent-glow)';
    t.style.display = 'block';
    setTimeout(() => { t.style.display = 'none'; }, 3000);
}

function formatRelativeTime(dateString) {
    const now = new Date();
    const past = new Date(dateString);
    const diffInSeconds = Math.floor((now - past) / 1000);

    if (diffInSeconds < 60) return 'ahora mismo';

    const diffInMinutes = Math.floor(diffInSeconds / 60);
    if (diffInMinutes < 60) return `hace ${diffInMinutes}m`;

    const diffInHours = Math.floor(diffInMinutes / 60);
    if (diffInHours < 24) return `hace ${diffInHours}h`;

    const diffInDays = Math.floor(diffInHours / 24);
    if (diffInDays === 1) return 'ayer';
    if (diffInDays < 7) return `hace ${diffInDays}d`;

    return past.toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit' });
}

function filterTopics() {
    const searchTerm = document.getElementById('search-input').value.toLowerCase();
    const cards = document.querySelectorAll('.topic-card');

    cards.forEach(card => {

        const title = card.querySelector('.card-header div').innerText.toLowerCase();

        if (title.includes(searchTerm)) {
            card.style.display = 'flex'; // Muestra la tarjeta
            card.style.animation = 'none'; // Evitamos que la animación se repita al escribir
        } else {
            card.style.display = 'none'; // Oculta la tarjeta
        }
    });

    const container = document.getElementById('topics-list');
    const visibleCards = container.querySelectorAll('.topic-card[style*="display: flex"]');

}