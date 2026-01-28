/**
 * Mobile Menu JavaScript - SIOSeG
 * Funcionalidade para menu responsivo mobile
 */

function initMobileMenu() {
    var toggle = document.getElementById('mobile-menu-toggle');
    var nav = document.getElementById('main-navigation');
    
    if (!toggle || !nav) return;
    
    // Menu toggle
    toggle.onclick = function() {
        if (nav.style.display === 'block') {
            nav.style.display = 'none';
            this.querySelector('i').className = 'fas fa-bars';
        } else {
            nav.style.display = 'block';
            this.querySelector('i').className = 'fas fa-times';
        }
    };
    
    // Submenus
    var submenus = nav.querySelectorAll('.has-submenu > a');
    for (var i = 0; i < submenus.length; i++) {
        submenus[i].onclick = function(e) {
            if (window.innerWidth <= 768) {
                e.preventDefault();
                var submenu = this.parentElement.querySelector('.submenu');
                if (submenu) {
                    if (submenu.style.display === 'block') {
                        submenu.style.display = 'none';
                    } else {
                        submenu.style.display = 'block';
                    }
                }
            }
        };
    }
}

if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initMobileMenu);
} else {
    initMobileMenu();
}