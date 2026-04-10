// Mostrar toast automáticamente cuando hay mensaje
document.addEventListener('DOMContentLoaded', function() {
    const toastElement = document.getElementById('feedback-toast');
    if (toastElement) {
        // Mostrar el toast
        toastElement.classList.add('show');
 
        // Ocultar automáticamente después de 4 segundos
        setTimeout(() => {
            toastElement.classList.remove('show');
        }, 10000);
    }
});