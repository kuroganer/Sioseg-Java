let materialCount = 1;

document.addEventListener('DOMContentLoaded', function() {
    // Carregar dados
    carregarClientes();
    carregarTecnicos();
    carregarProdutos();
    
    // Definir data atual
    const dataAgendamento = document.getElementById('dataAgendamento');
    if (dataAgendamento) {
        const agora = new Date();
        const dataFormatada = agora.toISOString().slice(0, 16);
        dataAgendamento.value = dataFormatada;
    }
    
    // Inicializar progress bar
    updateProgressBar();
    
    // Adicionar listeners para atualizar progress bar
    document.addEventListener('input', updateProgressBar);
    document.addEventListener('change', updateProgressBar);
    
    // Garantir que campos de data vazios não causem problemas
    const dataEncerramentoField = document.getElementById('dataEncerramento');
    if (dataEncerramentoField) {
        dataEncerramentoField.removeAttribute('required');
        
        dataEncerramentoField.addEventListener('blur', function() {
            if (this.value === '') {
                this.removeAttribute('required');
            }
        });
        
        const form = dataEncerramentoField.closest('form');
        if (form) {
            form.addEventListener('submit', function(e) {
                const dataEncerramento = document.getElementById('dataEncerramento');
                if (dataEncerramento && dataEncerramento.value === '') {
                    dataEncerramento.removeAttribute('required');
                }
            });
        }
    }
});

function carregarClientes() {
    const clientesList = document.getElementById('clientesList');
    if (clientesList) {
        const clientes = [
            { id: 2, nome: 'Bem estar LTDA.', documento: '21.932.707/0001-06', tipo: 'juridica' },
            { id: 11, nome: 'João da Silva', documento: '108.914.890-96', tipo: 'fisica' }
        ];
        
        clientes.forEach(cliente => {
            const option = document.createElement('option');
            const displayText = cliente.nome + ' - ' + cliente.documento;
            option.value = displayText;
            option.setAttribute('data-id', cliente.id);
            clientesList.appendChild(option);
        });
    }
}

function carregarTecnicos() {
    const tecnicoSelect = document.getElementById('idTecFk');
    if (tecnicoSelect) {
        const tecnicos = [
            { id: 3, nome: 'Amanda' },
            { id: 4, nome: 'Carlos Henrique da Silva' }
        ];
        
        tecnicos.forEach(tecnico => {
            const option = document.createElement('option');
            option.value = tecnico.id;
            option.textContent = tecnico.nome;
            tecnicoSelect.appendChild(option);
        });
    }
}

function carregarProdutos() {
    const produtoSelect = document.getElementById('produto1');
    if (produtoSelect) {
        const produtos = [
            { id: 1, nome: 'Parafuso Phillips', marca: 'Tramontina', modelo: '3,5x25mm', estoque: 451 },
            { id: 2, nome: 'Cabo de Fibra Óptica Monomodo 2 Fibras', marca: 'Furukawa', modelo: 'Gigalan OS2 Loose Tube', estoque: 100 }
        ];
        
        produtos.forEach(produto => {
            const option = document.createElement('option');
            option.value = produto.id;
            option.setAttribute('data-estoque', produto.estoque);
            option.textContent = produto.nome + ' - ' + produto.marca + ' - ' + produto.modelo + ' (Estoque: ' + produto.estoque + ')';
            produtoSelect.appendChild(option);
        });
    }
}

function setClienteId(value) {
    const options = document.querySelectorAll('#clientesList option');
    for (let option of options) {
        if (option.value === value) {
            document.getElementById('idCliFk').value = option.getAttribute('data-id');
            updateProgressBar();
            return;
        }
    }
    document.getElementById('idCliFk').value = '';
    updateProgressBar();
}

// Event listener para o input de cliente
document.addEventListener('DOMContentLoaded', function() {
    const clienteInput = document.getElementById('clienteInput');
    if (clienteInput) {
        clienteInput.addEventListener('change', function() {
            setClienteId(this.value);
        });
        
        clienteInput.addEventListener('input', function() {
            const currentValue = this.value;
            const options = document.querySelectorAll('#clientesList option');
            let found = false;
            
            for (let option of options) {
                if (option.value === currentValue) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                document.getElementById('idCliFk').value = '';
                updateProgressBar();
            }
        });
    }
    
    // Validação do formulário
    const form = document.querySelector('form');
    if (form) {
        form.addEventListener('submit', function(e) {
            const clienteId = document.getElementById('idCliFk').value;
            if (!clienteId) {
                e.preventDefault();
                alert('⚠️ Por favor, selecione um cliente válido da lista.');
                document.getElementById('clienteInput').focus();
                return;
            }
            
            const requiredFields = form.querySelectorAll('input[required], select[required]');
            let hasError = false;
            
            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    field.style.borderColor = '#dc3545';
                    hasError = true;
                } else {
                    field.style.borderColor = '';
                }
            });
            
            if (hasError) {
                e.preventDefault();
                alert('⚠️ Por favor, preencha todos os campos obrigatórios.');
            }
        });
    }
    
    // Limpar erros visuais
    document.addEventListener('input', function(e) {
        if (e.target.value.trim()) {
            e.target.style.borderColor = '';
        }
    });
});

function adicionarMaterial() {
    materialCount++;
    const container = document.getElementById('materiaisContainer');

    const materialItem = document.createElement('div');
    materialItem.className = 'material-item';
    materialItem.innerHTML = `
        <div class="form-row">
            <div class="form-group" style="flex: 2;">
                <label for="produto${materialCount}">Produto:</label>
                <div class="input-group">
                    <i class="fa-solid fa-box input-icon"></i>
                    <select id="produto${materialCount}" name="produtos[${materialCount}][idProd]" required>
                        <option value="">Selecione um produto</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="quantidade${materialCount}">Quantidade:</label>
                <div class="input-group">
                    <i class="fa-solid fa-hashtag input-icon"></i>
                    <input type="number" id="quantidade${materialCount}" name="produtos[${materialCount}][quantidade]" min="1" value="1" required>
                </div>
            </div>
            <div class="form-group">
                <label>&nbsp;</label>
                <button type="button" class="btn-remove-material" onclick="removerMaterial(this)">
                    <i class="fa-solid fa-minus"></i> Remover
                </button>
            </div>
        </div>
    `;

    container.appendChild(materialItem);
    
    // Carregar produtos no novo select
    const newSelect = materialItem.querySelector('select');
    const produtos = [
        { id: 1, nome: 'Parafuso Phillips', marca: 'Tramontina', modelo: '3,5x25mm', estoque: 451 },
        { id: 2, nome: 'Cabo de Fibra Óptica Monomodo 2 Fibras', marca: 'Furukawa', modelo: 'Gigalan OS2 Loose Tube', estoque: 100 }
    ];
    
    produtos.forEach(produto => {
        const option = document.createElement('option');
        option.value = produto.id;
        option.setAttribute('data-estoque', produto.estoque);
        option.textContent = produto.nome + ' - ' + produto.marca + ' - ' + produto.modelo + ' (Estoque: ' + produto.estoque + ')';
        newSelect.appendChild(option);
    });
    
    atualizarMateriaisAdicionados();
    updateProgressBar();
}

function removerMaterial(button) {
    button.closest('.material-item').remove();
    materialCount--;
    atualizarMateriaisAdicionados();
    updateProgressBar();
}

function atualizarMateriaisAdicionados() {
    const materiaisAdicionados = document.getElementById('materiaisAdicionados');
    const materiaisListContent = document.getElementById('materiaisListContent');
    const materiais = [];

    // Coletar todos os materiais selecionados
    for (let i = 1; i <= materialCount; i++) {
        const produtoSelect = document.getElementById(`produto${i}`);
        const quantidadeInput = document.getElementById(`quantidade${i}`);

        if (produtoSelect && quantidadeInput && produtoSelect.value) {
            const option = produtoSelect.options[produtoSelect.selectedIndex];
            const estoque = option.getAttribute('data-estoque');
            const quantidade = quantidadeInput.value;

            materiais.push({
                nome: option.text.split(' (Estoque:')[0],
                quantidade: quantidade,
                estoque: estoque
            });
        }
    }

    if (materiais.length > 0) {
        materiaisListContent.innerHTML = materiais.map(material =>
            `<div class="material-item-summary">
                <span>${material.nome}</span>
                <span>Quantidade: ${material.quantidade}</span>
                <span>Estoque: ${material.estoque}</span>
            </div>`
        ).join('');
        materiaisAdicionados.style.display = 'block';
    } else {
        materiaisAdicionados.style.display = 'none';
    }
}

// Função para atualizar progress bar
function updateProgressBar() {
    const form = document.querySelector('form');
    if (!form) return;
    
    const requiredFields = form.querySelectorAll('input[required], select[required]');
    const filledFields = Array.from(requiredFields).filter(field => {
        if (field.type === 'hidden') return field.value.trim() !== '';
        return field.value.trim() !== '';
    });
    
    const progress = requiredFields.length > 0 ? (filledFields.length / requiredFields.length) * 100 : 0;
    const progressBar = document.getElementById('progress-bar');
    if (progressBar) {
        progressBar.style.width = progress + '%';
    }
}

// Atualizar materiais adicionados quando houver mudança
document.addEventListener('change', function(e) {
    if (e.target && e.target.id && (e.target.id.startsWith('produto') || e.target.id.startsWith('quantidade'))) {
        atualizarMateriaisAdicionados();
    }
    updateProgressBar();
});