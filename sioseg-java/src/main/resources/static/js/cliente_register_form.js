document.addEventListener('DOMContentLoaded', function() {
    const tipoPessoaSelect = document.getElementById('tipoPessoa');
    const camposFisica = document.getElementById('campos-fisica');
    const camposJuridica = document.getElementById('campos-juridica');
    
    // Função para alternar campos baseado no tipo de pessoa
    function toggleCamposPessoa() {
        const tipo = tipoPessoaSelect.value;
        
        if (tipo === 'fisica') {
            camposFisica.style.display = 'block';
            camposJuridica.style.display = 'none';
            // Tornar campos PF obrigatórios
            setRequired('nomeCli', true);
            setRequired('cpfCli', true);
            setRequired('razaoSocial', false);
            setRequired('cnpj', false);
        } else if (tipo === 'juridica') {
            camposFisica.style.display = 'none';
            camposJuridica.style.display = 'block';
            // Tornar campos PJ obrigatórios
            setRequired('nomeCli', false);
            setRequired('cpfCli', false);
            setRequired('razaoSocial', true);
            setRequired('cnpj', true);
        } else {
            camposFisica.style.display = 'none';
            camposJuridica.style.display = 'none';
            // Remover obrigatoriedade
            setRequired('nomeCli', false);
            setRequired('cpfCli', false);
            setRequired('razaoSocial', false);
            setRequired('cnpj', false);
        }
    }
    
    function setRequired(fieldId, required) {
        const field = document.getElementById(fieldId);
        if (field) {
            field.required = required;
        }
    }
    
    // Event listener para mudança de tipo
    tipoPessoaSelect.addEventListener('change', toggleCamposPessoa);
    
    // Máscaras de formatação
    function maskCPF(value) {
        return value
            .replace(/\D/g, '')
            .replace(/(\d{3})(\d)/, '$1.$2')
            .replace(/(\d{3})(\d)/, '$1.$2')
            .replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    }
    
    function maskCNPJ(value) {
        return value
            .replace(/\D/g, '')
            .replace(/(\d{2})(\d)/, '$1.$2')
            .replace(/(\d{3})(\d)/, '$1.$2')
            .replace(/(\d{3})(\d)/, '$1/$2')
            .replace(/(\d{4})(\d{1,2})$/, '$1-$2');
    }
    
    function maskPhone(value) {
        value = value.replace(/\D/g, '');
        if (value.length <= 10) {
            return value.replace(/(\d{2})(\d{4})(\d{0,4})/, '($1) $2-$3');
        } else {
            return value.replace(/(\d{2})(\d{5})(\d{0,4})/, '($1) $2-$3');
        }
    }
    
    function maskCEP(value) {
        return value.replace(/\D/g, '').replace(/(\d{5})(\d{3})$/, '$1-$2');
    }
    
    // Aplicar máscaras
    const cpfInput = document.getElementById('cpfCli');
    const cnpjInput = document.getElementById('cnpj');
    const tel1Input = document.getElementById('tel1Cli');
    const tel2Input = document.getElementById('tel2Cli');
    const cepInput = document.getElementById('cep');
    
    if (cpfInput) {
        cpfInput.addEventListener('input', function() {
            this.value = maskCPF(this.value);
        });
    }
    
    if (cnpjInput) {
        cnpjInput.addEventListener('input', function() {
            this.value = maskCNPJ(this.value);
        });
    }
    
    if (tel1Input) {
        tel1Input.addEventListener('input', function() {
            this.value = maskPhone(this.value);
        });
    }
    
    if (tel2Input) {
        tel2Input.addEventListener('input', function() {
            this.value = maskPhone(this.value);
        });
    }
    
    if (cepInput) {
        cepInput.addEventListener('input', function() {
            this.value = maskCEP(this.value);
        });
        
        // Buscar CEP
        cepInput.addEventListener('blur', function() {
            const cep = this.value.replace(/\D/g, '');
            if (cep.length === 8) {
                fetch(`https://viacep.com.br/ws/${cep}/json/`)
                    .then(response => response.json())
                    .then(data => {
                        if (!data.erro) {
                            document.getElementById('logradouro').value = data.logradouro;
                            document.getElementById('bairro').value = data.bairro;
                            document.getElementById('cidade').value = data.localidade;
                            document.getElementById('uf').value = data.uf;
                            updateEnderecoCompleto();
                        }
                    })
                    .catch(error => console.log('Erro ao buscar CEP:', error));
            }
        });
    }
    
    // Atualizar endereço completo
    function updateEnderecoCompleto() {
        const logradouro = document.getElementById('logradouro').value;
        const numero = document.getElementById('numEnd').value;
        const complemento = document.getElementById('complemento').value;
        const bairro = document.getElementById('bairro').value;
        const cidade = document.getElementById('cidade').value;
        const uf = document.getElementById('uf').value;
        
        let endereco = '';
        if (logradouro) endereco += logradouro;
        if (numero) endereco += ', ' + numero;
        if (complemento) endereco += ', ' + complemento;
        if (bairro) endereco += ' - ' + bairro;
        if (cidade) endereco += ', ' + cidade;
        if (uf) endereco += '/' + uf;
        
        document.getElementById('endereco').value = endereco;
    }
    
    // Listeners para atualizar endereço
    ['logradouro', 'numEnd', 'complemento', 'bairro', 'cidade', 'uf'].forEach(function(id) {
        const field = document.getElementById(id);
        if (field) {
            field.addEventListener('input', updateEnderecoCompleto);
        }
    });
    
    // Validação de senhas
    const senhaInput = document.getElementById('senha');
    const confirmarSenhaInput = document.getElementById('confirmarSenha');
    
    function validatePasswords() {
        if (senhaInput && confirmarSenhaInput) {
            if (senhaInput.value !== confirmarSenhaInput.value) {
                confirmarSenhaInput.setCustomValidity('As senhas não coincidem');
            } else {
                confirmarSenhaInput.setCustomValidity('');
            }
        }
    }
    
    if (senhaInput) senhaInput.addEventListener('input', validatePasswords);
    if (confirmarSenhaInput) confirmarSenhaInput.addEventListener('input', validatePasswords);
    
    // Inicializar
    toggleCamposPessoa();
});