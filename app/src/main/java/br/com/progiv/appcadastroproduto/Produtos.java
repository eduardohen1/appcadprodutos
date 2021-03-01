package br.com.progiv.appcadastroproduto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.progiv.appcadastroproduto.DAO.ProdutosDAO;
import br.com.progiv.appcadastroproduto.Model.ProdutosModel;

public class Produtos extends AppCompatActivity {
    EditText txtNomeProduto;
    EditText txtDescricaoProduto;
    EditText txtQuantidadeProduto;
    Button btnModificar;
    ProdutosModel editarProduto;
    ProdutosModel produto;
    ProdutosDAO produtosDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        produto = new ProdutosModel();
        produtosDAO = new ProdutosDAO(Produtos.this);

        Intent intent = getIntent();
        editarProduto = (ProdutosModel) intent.getSerializableExtra("selectProduto"); //criando um alias do Main para Produtos

        txtNomeProduto = (EditText) findViewById(R.id.nomeProduto);
        txtDescricaoProduto = (EditText) findViewById(R.id.descricaoProduto);
        txtQuantidadeProduto = (EditText) findViewById(R.id.quantidadeProduto);
        btnModificar = (Button)findViewById(R.id.btnModificar);

        if(editarProduto != null){
            btnModificar.setText("Modificar");
            txtNomeProduto.setText(editarProduto.getNome());
            txtDescricaoProduto.setText(editarProduto.getDescricao());
            txtQuantidadeProduto.setText(String.valueOf(editarProduto.getQuantidade()));
            produto.setId(editarProduto.getId());
        }else{
            btnModificar.setText("Cadastrar");
        }

        //selecionar 1 item e exibir na tela de cadastro de produto:
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produto.setNome(txtNomeProduto.getText().toString());
                produto.setDescricao(txtDescricaoProduto.getText().toString());
                produto.setQuantidade(Integer.parseInt(txtQuantidadeProduto.getText().toString()));
                if(btnModificar.getText().toString().equals("Cadastrar")){
                    //insert
                    produtosDAO.salvarProduto(produto);
                }else{
                    //update
                    produtosDAO.alterarProduto(produto);
                }
                produtosDAO.close();
                finish();
            }
        });

    }
}