-- ============================================
-- USUARIOS (Users)
-- ============================================
CREATE TABLE IF NOT EXISTS usuarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(50) NOT NULL DEFAULT 'cliente',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT true
);

-- ============================================
-- CLIENTES (Customers)
-- ============================================
CREATE TABLE IF NOT EXISTS clientes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL UNIQUE REFERENCES usuarios(id) ON DELETE CASCADE,
    telefone VARCHAR(20),
    cpf VARCHAR(11) UNIQUE,
    data_nascimento DATE,
    genero VARCHAR(10),
    endereco VARCHAR(255),
    numero VARCHAR(10),
    complemento VARCHAR(255),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(2),
    cep VARCHAR(10),
    pontos_fidelidade INT DEFAULT 0,
    data_ultimo_acesso TIMESTAMP,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- FORNECEDORES (Suppliers)
-- ============================================
CREATE TABLE IF NOT EXISTS fornecedores (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    cnpj VARCHAR(18) UNIQUE,
    email VARCHAR(255),
    telefone VARCHAR(20),
    endereco VARCHAR(255),
    cidade VARCHAR(100),
    estado VARCHAR(2),
    cep VARCHAR(10),
    custo_medio DECIMAL(10,2) DEFAULT 0,
    rentabilidade DECIMAL(10,2) DEFAULT 0,
    ativo BOOLEAN DEFAULT true,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- CATEGORIAS (Categories)
-- ============================================
CREATE TABLE IF NOT EXISTS categorias (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT,
    imagem_url VARCHAR(500),
    slug VARCHAR(100) UNIQUE,
    ativo BOOLEAN DEFAULT true,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- MARCAS (Brands)
-- ============================================
CREATE TABLE IF NOT EXISTS marcas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL UNIQUE,
    logo_url VARCHAR(500),
    slug VARCHAR(100) UNIQUE,
    ativo BOOLEAN DEFAULT true,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- PRODUTOS (Products)
-- ============================================
CREATE TABLE IF NOT EXISTS produtos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    categoria_id UUID REFERENCES categorias(id),
    marca_id UUID REFERENCES marcas(id),
    fornecedor_id UUID REFERENCES fornecedores(id),
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    descricao_detalhada TEXT,
    slug VARCHAR(255) UNIQUE,
    preco DECIMAL(10,2) NOT NULL,
    preco_custo DECIMAL(10,2),
    margem DECIMAL(10,2),
    estoque INT DEFAULT 0,
    disponivel BOOLEAN DEFAULT true,
    peso DECIMAL(8,3),
    dimensoes VARCHAR(100),
    imagem_principal_url VARCHAR(500),
    ativo BOOLEAN DEFAULT true,
    views_count INT DEFAULT 0,
    favoritos_count INT DEFAULT 0,
    avaliacoes_media DECIMAL(3,2) DEFAULT 0,
    seo_title VARCHAR(255),
    seo_description VARCHAR(500),
    seo_keywords VARCHAR(500),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- IMAGENS_PRODUTOS (Product Images)
-- ============================================
CREATE TABLE IF NOT EXISTS imagens_produtos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    produto_id UUID NOT NULL REFERENCES produtos(id) ON DELETE CASCADE,
    url VARCHAR(500) NOT NULL,
    ordem INT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- ESTOQUE (Stock)
-- ============================================
CREATE TABLE IF NOT EXISTS estoque (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    produto_id UUID NOT NULL UNIQUE REFERENCES produtos(id) ON DELETE CASCADE,
    quantidade INT NOT NULL DEFAULT 0,
    quantidade_reservada INT DEFAULT 0,
    quantidade_disponivel INT DEFAULT 0,
    data_ultima_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- AVALIACOES (Reviews)
-- ============================================
CREATE TABLE IF NOT EXISTS avaliacoes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    produto_id UUID NOT NULL REFERENCES produtos(id) ON DELETE CASCADE,
    cliente_id UUID NOT NULL REFERENCES clientes(id) ON DELETE CASCADE,
    titulo VARCHAR(200),
    descricao TEXT,
    nota INT NOT NULL CHECK (nota >= 1 AND nota <= 5),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- PEDIDOS (Orders)
-- ============================================
CREATE TABLE IF NOT EXISTS pedidos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cliente_id UUID NOT NULL REFERENCES clientes(id),
    numero_pedido VARCHAR(20) UNIQUE NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'pendente',
    valor_subtotal DECIMAL(10,2),
    valor_desconto DECIMAL(10,2) DEFAULT 0,
    valor_frete DECIMAL(10,2) DEFAULT 0,
    valor_total DECIMAL(10,2),
    cupom_id UUID,
    observacoes TEXT,
    endereco_entrega VARCHAR(500),
    rastreamento VARCHAR(100),
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_confirmacao TIMESTAMP,
    data_envio TIMESTAMP,
    data_entrega TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- ITENS_PEDIDO (Order Items)
-- ============================================
CREATE TABLE IF NOT EXISTS itens_pedido (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pedido_id UUID NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    produto_id UUID NOT NULL REFERENCES produtos(id),
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- CARRINHO (Shopping Cart)
-- ============================================
CREATE TABLE IF NOT EXISTS carrinho (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cliente_id UUID NOT NULL REFERENCES clientes(id) ON DELETE CASCADE,
    produto_id UUID NOT NULL REFERENCES produtos(id),
    quantidade INT NOT NULL DEFAULT 1,
    preco_unitario DECIMAL(10,2),
    data_adicao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- CARRINHO_ABANDONADO (Abandoned Cart)
-- ============================================
CREATE TABLE IF NOT EXISTS carrinho_abandonado (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cliente_id UUID REFERENCES clientes(id),
    email VARCHAR(255),
    telefone VARCHAR(20),
    produtos_info JSONB,
    quantidade_itens INT,
    valor_estimado DECIMAL(10,2),
    motivo_abandono VARCHAR(255),
    data_abandono TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notificacao_enviada BOOLEAN DEFAULT false,
    data_notificacao TIMESTAMP,
    recuperado BOOLEAN DEFAULT false,
    data_recuperacao TIMESTAMP
);

-- ============================================
-- CUPONS (Coupons)
-- ============================================
CREATE TABLE IF NOT EXISTS cupons (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(50) UNIQUE NOT NULL,
    descricao TEXT,
    tipo VARCHAR(20) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    limite_uso INT,
    usos INT DEFAULT 0,
    data_inicio TIMESTAMP,
    data_fim TIMESTAMP,
    ativo BOOLEAN DEFAULT true,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- FAVORITOS (Favorites)
-- ============================================
CREATE TABLE IF NOT EXISTS favoritos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cliente_id UUID NOT NULL REFERENCES clientes(id) ON DELETE CASCADE,
    produto_id UUID NOT NULL REFERENCES produtos(id) ON DELETE CASCADE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(cliente_id, produto_id)
);

-- ============================================
-- VISITANTES (Visitors)
-- ============================================
CREATE TABLE IF NOT EXISTS visitantes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sessao_id VARCHAR(255) UNIQUE NOT NULL,
    cliente_id UUID REFERENCES clientes(id),
    email_anonimo VARCHAR(255),
    telefone_anonimo VARCHAR(20),
    data_visita TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_ultima_atividade TIMESTAMP,
    origem VARCHAR(50),
    dispositivo VARCHAR(50),
    navegador VARCHAR(100),
    sistema_operacional VARCHAR(100),
    ip_address VARCHAR(45),
    cidade VARCHAR(100),
    estado VARCHAR(2),
    pais VARCHAR(100),
    tempo_total_segundos INT DEFAULT 0
);

-- ============================================
-- EVENTOS_SITE (Site Events)
-- ============================================
CREATE TABLE IF NOT EXISTS eventos_site (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sessao_id VARCHAR(255),
    visitante_id UUID REFERENCES visitantes(id),
    tipo_evento VARCHAR(50) NOT NULL,
    pagina VARCHAR(255),
    produto_id UUID REFERENCES produtos(id),
    dados_adicionais JSONB,
    data_evento TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- CAMPANHAS (Campaigns)
-- ============================================
CREATE TABLE IF NOT EXISTS campanhas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    tipo VARCHAR(50),
    status VARCHAR(50) DEFAULT 'rascunho',
    data_inicio TIMESTAMP,
    data_fim TIMESTAMP,
    destinatarios JSONB,
    mensagem TEXT,
    taxa_abertura DECIMAL(5,2) DEFAULT 0,
    taxa_clique DECIMAL(5,2) DEFAULT 0,
    taxa_conversao DECIMAL(5,2) DEFAULT 0,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- MENSAGENS (Chat Messages)
-- ============================================
CREATE TABLE IF NOT EXISTS mensagens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cliente_id UUID NOT NULL REFERENCES clientes(id),
    remetente VARCHAR(50) NOT NULL,
    conteudo TEXT NOT NULL,
    arquivo_url VARCHAR(500),
    respondida BOOLEAN DEFAULT false,
    data_resposta TIMESTAMP,
    resposta_conteudo TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- PAGAMENTOS (Payments)
-- ============================================
CREATE TABLE IF NOT EXISTS pagamentos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pedido_id UUID NOT NULL REFERENCES pedidos(id),
    metodo VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'pendente',
    valor DECIMAL(10,2) NOT NULL,
    referencia_externa VARCHAR(255),
    data_pagamento TIMESTAMP,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- COMPRAS (Purchases)
-- ============================================
CREATE TABLE IF NOT EXISTS compras (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    fornecedor_id UUID NOT NULL REFERENCES fornecedores(id),
    numero_compra VARCHAR(50) UNIQUE,
    status VARCHAR(50) DEFAULT 'pendente',
    valor_total DECIMAL(10,2),
    data_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_recebimento TIMESTAMP,
    observacoes TEXT
);

-- ============================================
-- ITENS_COMPRA (Purchase Items)
-- ============================================
CREATE TABLE IF NOT EXISTS itens_compra (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    compra_id UUID NOT NULL REFERENCES compras(id) ON DELETE CASCADE,
    produto_id UUID NOT NULL REFERENCES produtos(id),
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2)
);

-- ============================================
-- NOTAS_FISCAIS (Invoices)
-- ============================================
CREATE TABLE IF NOT EXISTS notas_fiscais (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pedido_id UUID REFERENCES pedidos(id),
    numero VARCHAR(50),
    serie VARCHAR(10),
    chave_acesso VARCHAR(44),
    status VARCHAR(50) DEFAULT 'pendente',
    data_emissao TIMESTAMP,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- AUDITORIA (Audit Log)
-- ============================================
CREATE TABLE IF NOT EXISTS auditoria (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID REFERENCES usuarios(id),
    tabela VARCHAR(100),
    registro_id UUID,
    acao VARCHAR(50),
    dados_anteriores JSONB,
    dados_novos JSONB,
    data_acao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45)
);

-- ============================================
-- LOGS (System Logs)
-- ============================================
CREATE TABLE IF NOT EXISTS logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nivel VARCHAR(20),
    mensagem TEXT,
    stacktrace TEXT,
    usuario_id UUID REFERENCES usuarios(id),
    data_log TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- CONSENTIMENTO_COOKIES (Cookie Consent)
-- ============================================
CREATE TABLE IF NOT EXISTS consentimento_cookies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sessao_id VARCHAR(255),
    cliente_id UUID REFERENCES clientes(id),
    consentimento_necessario BOOLEAN DEFAULT true,
    consentimento_estatistico BOOLEAN DEFAULT false,
    consentimento_marketing BOOLEAN DEFAULT false,
    data_consentimento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- INDICES
-- ============================================
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_clientes_usuario_id ON clientes(usuario_id);
CREATE INDEX idx_clientes_cidade_estado ON clientes(cidade, estado);
CREATE INDEX idx_produtos_categoria ON produtos(categoria_id);
CREATE INDEX idx_produtos_marca ON produtos(marca_id);
CREATE INDEX idx_produtos_nome ON produtos(nome);
CREATE INDEX idx_pedidos_cliente ON pedidos(cliente_id);
CREATE INDEX idx_pedidos_status ON pedidos(status);
CREATE INDEX idx_pedidos_data ON pedidos(data_pedido);
CREATE INDEX idx_carrinho_cliente ON carrinho(cliente_id);
CREATE INDEX idx_carrinho_abandonado_cliente ON carrinho_abandonado(cliente_id);
CREATE INDEX idx_carrinho_abandonado_email ON carrinho_abandonado(email);
CREATE INDEX idx_visitantes_sessao ON visitantes(sessao_id);
CREATE INDEX idx_visitantes_cliente ON visitantes(cliente_id);
CREATE INDEX idx_eventos_sessao ON eventos_site(sessao_id);
CREATE INDEX idx_eventos_visitante ON eventos_site(visitante_id);
CREATE INDEX idx_eventos_tipo ON eventos_site(tipo_evento);
CREATE INDEX idx_eventos_data ON eventos_site(data_evento);
CREATE INDEX idx_mensagens_cliente ON mensagens(cliente_id);
CREATE INDEX idx_mensagens_respondida ON mensagens(respondida);
CREATE INDEX idx_pagamentos_pedido ON pagamentos(pedido_id);
CREATE INDEX idx_pagamentos_status ON pagamentos(status);
CREATE INDEX idx_auditoria_tabela ON auditoria(tabela);
CREATE INDEX idx_auditoria_usuario ON auditoria(usuario_id);
CREATE INDEX idx_logs_nivel ON logs(nivel);
CREATE INDEX idx_logs_data ON logs(data_log);
