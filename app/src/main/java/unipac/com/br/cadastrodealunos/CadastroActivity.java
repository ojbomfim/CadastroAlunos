package unipac.com.br.cadastrodealunos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText EdtNome;
    private EditText EdtEmail;
    private EditText EdtTelefone;
    private Button BtnExcluir;
    private Button BtnSalvar;
    private Button BtnCancelar;
    private ImageView imageView;

    private final Usuario usuario = new Usuario(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        EdtNome = (EditText)findViewById(R.id.EdtNome);
        EdtEmail = (EditText)findViewById(R.id.EdtEmail);
        EdtTelefone = (EditText)findViewById(R.id.EdtTelefone);
        BtnExcluir = (Button)findViewById(R.id.BtnExcluir);
        BtnCancelar = (Button)findViewById(R.id.BtnCancelar);
        BtnSalvar = (Button)findViewById(R.id.BtnSalvar);
        imageView = (ImageView)findViewById(R.id.imageViewAvatar);

        BtnExcluir.setOnClickListener(this);
        BtnSalvar.setOnClickListener(this);
        BtnCancelar.setOnClickListener(this);

        if (getIntent().getExtras() != null){
            setTitle(getString(R.string.titulo_editando));
            int codigo = getIntent().getExtras().getInt("consulta");
            usuario.carregaUsuarioPeloCodigo(codigo);

            EdtNome.setText(usuario.getNome().toString());
            EdtEmail.setText(usuario.getEmail().toString());
            EdtTelefone.setText(usuario.getTelefone().toString());
        }else{
            setTitle(getString(R.string.titulo_incluindo));
        }

        BtnExcluir.setEnabled(true);
        if (usuario.getCodigo() == -1)
            BtnExcluir.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.BtnCancelar: {
                finish();
                break;
            }
            case R.id.BtnExcluir: {
                usuario.excluir();
                Toast.makeText(CadastroActivity.this,"Usuário Excluido com Sucesso!", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(CadastroActivity.this, ConsultaActivity.class);
                startActivity(intent2);
                finish();
                break;
            }
            case R.id.BtnSalvar:{
                boolean valido = true;
                usuario.setNome(EdtNome.getText().toString().trim());
                usuario.setEmail(EdtEmail.getText().toString().trim().toLowerCase());
                usuario.setTelefone(EdtTelefone.getText().toString().trim());
                carregaImagem();

                if (usuario.getNome().equals("")){
                    EdtNome.setError(getString(R.string.obrigatorio));
                    valido = false;
                }

                if (usuario.getEmail().equals("")){
                    EdtEmail.setError(getString(R.string.obrigatorio));
                    valido = false;
                }

                if (usuario.getTelefone().equals("")){
                    EdtTelefone.setError(getString(R.string.obrigatorio));
                    valido = false;
                }

                if (valido){
                    usuario.salvar();
                    Toast.makeText(CadastroActivity.this,"Usuário Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            }
        }
    }

    private void carregaImagem(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                usuario.setImagem(Auxilio.getImagemBytesFromUrl(usuario.getUrlGravatar()));
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(usuario.getAvatar());
                    }
                });
            }
        });
        thread.start();
        try{
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

