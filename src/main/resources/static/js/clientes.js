const form = document.getElementById("formCliente");
const lista = document.getElementById("listaClientes");
const busca = document.getElementById("buscaCliente");

let clientes = [];

async function carregarClientes() {
    try {
        const resposta = await fetch("/clientes");

        if (!resposta.ok) {
            throw new Error("Não foi possível carregar os clientes.");
        }

        clientes = await resposta.json();
        renderizarClientes(clientes);
    } catch (erro) {
        console.error(erro);
        alert("Erro ao carregar clientes. Verifique se o servidor está rodando.");
    }
}

function renderizarClientes(dados) {
    lista.innerHTML = "";

    if (dados.length === 0) {
        const linha = document.createElement("tr");
        const coluna = document.createElement("td");
        coluna.colSpan = 5;

        const mensagem = document.createElement("div");
        mensagem.className = "empty-state";

        const titulo = document.createElement("h3");
        titulo.textContent = "Nenhum cliente encontrado";

        const texto = document.createElement("p");
        texto.textContent = "Cadastre um cliente para exibi-lo nesta tabela.";

        mensagem.append(titulo, texto);
        coluna.appendChild(mensagem);
        linha.appendChild(coluna);
        lista.appendChild(linha);
        return;
    }

    dados.forEach(cliente => {
        const linha = document.createElement("tr");

        const id = document.createElement("td");
        id.textContent = cliente.id;

        const nome = document.createElement("td");
        nome.textContent = cliente.nome;

        const email = document.createElement("td");
        email.textContent = cliente.email;

        const telefone = document.createElement("td");
        telefone.textContent = cliente.telefone || "-";

        const acoes = document.createElement("td");
        const botaoExcluir = document.createElement("button");

        botaoExcluir.type = "button";
        botaoExcluir.textContent = "Excluir";
        botaoExcluir.className = "secondary-button";
        botaoExcluir.dataset.id = cliente.id;

        acoes.appendChild(botaoExcluir);
        linha.append(id, nome, email, telefone, acoes);
        lista.appendChild(linha);
    });
}

form.addEventListener("submit", async (evento) => {
    evento.preventDefault();

    const dados = {
        nome: form.nome.value.trim(),
        email: form.email.value.trim(),
        telefone: form.telefone.value.trim() || null
    };

    try {
        const resposta = await fetch("/clientes", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dados)
        });

        if (!resposta.ok) {
            const erro = await resposta.json().catch(() => null);
            throw new Error(
                erro?.message || erro?.detail || "Não foi possível cadastrar o cliente."
            );
        }

        alert("Cliente cadastrado com sucesso!");
        form.reset();
        await carregarClientes();

    } catch (erro) {
        alert(erro.message);
    }
});

busca.addEventListener("input", () => {
    const termo = busca.value.toLowerCase().trim();

    const filtrados = clientes.filter(cliente =>
        cliente.nome.toLowerCase().includes(termo) ||
        cliente.email.toLowerCase().includes(termo)
    );

    renderizarClientes(filtrados);
});

lista.addEventListener("click", async (evento) => {
    const botao = evento.target.closest("button[data-id]");

    if (!botao) return;

    const id = botao.dataset.id;

    if (!confirm("Tem certeza que deseja excluir este cliente?")) {
        return;
    }

    try {
        const resposta = await fetch(`/clientes/${id}`, {
            method: "DELETE"
        });

        if (!resposta.ok) {
            throw new Error("Não foi possível excluir o cliente.");
        }

        alert("Cliente excluído com sucesso!");
        await carregarClientes();

    } catch (erro) {
        alert(erro.message);
    }
});

carregarClientes();
