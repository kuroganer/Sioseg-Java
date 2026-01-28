// Alert System JavaScript
document.addEventListener('DOMContentLoaded', function() {
    
    // Função para mostrar alertas personalizados
    function showAlert(message, type = 'info', duration = 5000) {
        const alertContainer = getOrCreateAlertContainer();
        
        const alert = document.createElement('div');
        alert.className = `alert alert-${type}`;
        alert.innerHTML = `
            <div class="alert-content">
                <i class="fas ${getAlertIcon(type)}"></i>
                <span class="alert-message">${message}</span>
                <button class="alert-close" onclick="closeAlert(this)">
                    <i class="fas fa-times"></i>
                </button>
            </div>
        `;
        
        alertContainer.appendChild(alert);
        
        // Auto-remover após duração especificada
        if (duration > 0) {
            setTimeout(() => {
                if (alert.parentNode) {
                    alert.remove();
                }
            }, duration);
        }
        
        return alert;
    }
    
    function getOrCreateAlertContainer() {
        let container = document.getElementById('alert-container');
        if (!container) {
            container = document.createElement('div');
            container.id = 'alert-container';
            container.className = 'alert-container';
            document.body.appendChild(container);
        }
        return container;
    }
    
    function getAlertIcon(type) {
        const icons = {
            'success': 'fa-check-circle',
            'error': 'fa-exclamation-circle',
            'warning': 'fa-exclamation-triangle',
            'info': 'fa-info-circle'
        };
        return icons[type] || icons['info'];
    }
    
    // Função global para fechar alerta
    window.closeAlert = function(button) {
        const alert = button.closest('.alert');
        if (alert) {
            alert.remove();
        }
    };
    
    // Expor função showAlert globalmente
    window.showAlert = showAlert;
    
    // Processar mensagens do servidor (se existirem)
    const successMessages = document.querySelectorAll('.success-message');
    const errorMessages = document.querySelectorAll('.error-message');
    
    successMessages.forEach(msg => {
        if (msg.textContent.trim()) {
            showAlert(msg.textContent.trim(), 'success');
            msg.style.display = 'none';
        }
    });
    
    errorMessages.forEach(msg => {
        if (msg.textContent.trim()) {
            showAlert(msg.textContent.trim(), 'error');
            msg.style.display = 'none';
        }
    });
});

// CSS para os alertas (injetado via JavaScript)
const alertStyles = `
.alert-container {
    position: fixed;
    top: 20px;
    right: 20px;
    z-index: 9999;
    max-width: 400px;
}

.alert {
    background: white;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    margin-bottom: 10px;
    overflow: hidden;
    animation: slideIn 0.3s ease-out;
}

.alert-success {
    border-left: 4px solid #28a745;
}

.alert-error {
    border-left: 4px solid #dc3545;
}

.alert-warning {
    border-left: 4px solid #ffc107;
}

.alert-info {
    border-left: 4px solid #17a2b8;
}

.alert-content {
    display: flex;
    align-items: center;
    padding: 15px;
    gap: 10px;
}

.alert-content i:first-child {
    font-size: 18px;
}

.alert-success .alert-content i:first-child {
    color: #28a745;
}

.alert-error .alert-content i:first-child {
    color: #dc3545;
}

.alert-warning .alert-content i:first-child {
    color: #ffc107;
}

.alert-info .alert-content i:first-child {
    color: #17a2b8;
}

.alert-message {
    flex: 1;
    font-size: 14px;
    color: #333;
}

.alert-close {
    background: none;
    border: none;
    color: #999;
    cursor: pointer;
    padding: 0;
    width: 20px;
    height: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.alert-close:hover {
    color: #666;
}

@keyframes slideIn {
    from {
        transform: translateX(100%);
        opacity: 0;
    }
    to {
        transform: translateX(0);
        opacity: 1;
    }
}

[data-theme='dark'] .alert {
    background: #2d3748;
    color: #e2e8f0;
}

[data-theme='dark'] .alert-message {
    color: #e2e8f0;
}
`;

// Injetar CSS
const styleSheet = document.createElement('style');
styleSheet.textContent = alertStyles;
document.head.appendChild(styleSheet);