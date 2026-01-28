/**
 * JavaScript para Dashboard Admin - SIOSeG
 * Versão simplificada focada apenas no dashboard
 */

function DashboardAdmin() {
    this.pieChart = null;
    this.ratingsChart = null;
    this.init();
}

DashboardAdmin.prototype.init = function() {
    var self = this;
    
    // Aguarda dados estarem disponíveis
    var retries = 0;
    var maxRetries = 5;
    
    var tryInit = function() {
        if (typeof window.pieChartData !== 'undefined' || retries >= maxRetries) {
            self.initCharts();
            self.initKanban();
            return;
        }
        retries++;
        setTimeout(tryInit, 100);
    };
    
    tryInit();
};

DashboardAdmin.prototype.initCharts = function() {
    this.initPieChart();
    this.initRatingsChart();
};

DashboardAdmin.prototype.initPieChart = function() {
    var ctx = document.getElementById('overviewPieChart');
    if (!ctx) return;

    var pieData = window.pieChartData || {
        abertas: 0, concluidas: 0, andamento: 0, avaliacoes: 0
    };

    try {
        this.pieChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: ['OS Abertas', 'OS Concluídas', 'Em Andamento', 'Avaliações Pendentes'],
                datasets: [{
                    data: [pieData.abertas, pieData.concluidas, pieData.andamento, pieData.avaliacoes],
                    backgroundColor: ['#F97316', '#16a34a', '#0ea5e9', '#1E3A8A'],
                    borderWidth: 2,
                    borderColor: '#fff'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'bottom',
                        labels: { padding: 20, usePointStyle: true }
                    }
                }
            }
        });
    } catch (err) {
        console.log('Erro ao criar pie chart:', err);
    }
};

DashboardAdmin.prototype.initRatingsChart = function() {
    var ctx = document.getElementById('ratingsChart');
    if (!ctx) return;

    var chartLabels = window.chartLabels || ['D-6', 'D-5', 'D-4', 'D-3', 'D-2', 'Ontem', 'Hoje'];
    var chartData = window.chartData || [0, 0, 0, 0, 0, 0, 0];

    try {
        this.ratingsChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: chartLabels,
                datasets: [{
                    label: 'Média de Avaliações',
                    data: chartData,
                    borderColor: '#1E3A8A',
                    backgroundColor: '#1E3A8A33',
                    borderWidth: 3,
                    tension: 0.4,
                    fill: true
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: { legend: { display: false } },
                scales: {
                    y: { beginAtZero: false, max: 5, min: 1 }
                }
            }
        });
    } catch (err) {
        console.log('Erro ao criar ratings chart:', err);
    }
};

DashboardAdmin.prototype.initKanban = function() {
    var tasks = document.querySelectorAll('.kanban-task');
    var lists = document.querySelectorAll('.task-list');
    
    var self = this;
    
    tasks.forEach(function(task) {
        task.draggable = true;
        task.addEventListener('dragstart', function(e) {
            e.dataTransfer.setData('text/plain', e.target.id);
            e.target.classList.add('dragging');
        });
        task.addEventListener('dragend', function(e) {
            e.target.classList.remove('dragging');
        });
    });
    
    lists.forEach(function(list) {
        list.addEventListener('dragover', function(e) {
            e.preventDefault();
            list.classList.add('drag-over');
        });
        list.addEventListener('dragleave', function(e) {
            list.classList.remove('drag-over');
        });
        list.addEventListener('drop', function(e) {
            e.preventDefault();
            list.classList.remove('drag-over');
            
            var taskId = e.dataTransfer.getData('text/plain');
            var task = document.getElementById(taskId);
            
            if (task && task !== e.target) {
                list.appendChild(task);
                self.updateTaskStatus(task, list.id);
            }
        });
    });
};

DashboardAdmin.prototype.updateTaskStatus = function(task, newListId) {
    task.style.opacity = '0.5';
    setTimeout(function() {
        task.style.opacity = '1';
    }, 500);
};

// Inicializa quando DOM estiver pronto
(function() {
    function init() {
        try {
            window.dashboardAdmin = new DashboardAdmin();
        } catch (e) {
            console.log('Erro ao inicializar dashboard:', e);
        }
    }
    
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();

// CSS para drag and drop
var style = document.createElement('style');
style.textContent = `
.kanban-task.dragging { opacity: 0.5; transform: rotate(2deg); }
.task-list.drag-over { background-color: rgba(30, 58, 138, 0.1); border: 2px dashed #1E3A8A; }
`;
document.head.appendChild(style);