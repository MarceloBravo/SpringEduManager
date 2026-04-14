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

const showToastJs = (title, message, type = 'info') => {
    const toastElement = document.getElementById('toast-js');
    const toastTitle = document.getElementById('toast-js-title');
    const toastBody = document.getElementById('toast-js-body');
    
    // Limpiar clases previas
    toastElement.classList.remove('toast-info', 'toast-success', 'toast-warning', 'toast-danger');
    
    toastTitle.textContent = title;
    toastBody.textContent = message;
    
    toastElement.classList.add('show');

    switch(type) {
        case 'info':
            toastElement.classList.add('toast-info');
            break;
        case 'success':
            toastElement.classList.add('toast-success');
            break;
        case 'warning':
            toastElement.classList.add('toast-warning');
            break;
        case 'danger':
            toastElement.classList.add('toast-danger');
            break;
        default:
            toastElement.classList.add('toast-info');
            break;
    }
    
    setTimeout(() => {
        toastElement.classList.remove('show');
    }, 10000);
};