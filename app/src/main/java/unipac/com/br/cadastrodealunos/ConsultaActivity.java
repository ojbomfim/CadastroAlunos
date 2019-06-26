package unipac.com.br.cadastrodealunos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ConsultaActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static String java;
    private ListView ListViewUsuarios;
    private Button BtnFechar;
    private UsuarioAdapter usuarioAdapter;
    private ArrayList<Usuario> usuarios;
    private Usuario usuarioEdicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

                BtnFechar = (Button) findViewById(R.id.BtnFechar);
                BtnFechar.setOnClickListener(this);

                ListViewUsuarios = (ListView) findViewById(R.id.ListViewUsuarios);
                ListViewUsuarios.setOnItemClickListener(this);

                usuarios = new Usuario(this).getUsuarios();
                usuarioAdapter = new UsuarioAdapter(this, usuarios);
                ListViewUsuarios.setAdapter(usuarioAdapter);
            }


            @Override
            public void onClick(View v) {
                finish();
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuario = usuarios.get(position);
                Intent intent = new Intent(this, CadastroActivity.class);
                intent.putExtra("consulta", usuario.getCodigo());
                usuarioEdicao = usuario;
                startActivity(intent);
            }

            @Override
            protected void onResume() {
                super.onResume();
                if (usuarioEdicao != null) {
                    usuarioEdicao.carregaUsuarioPeloCodigo(usuarioEdicao.getCodigo());
                    if (usuarioEdicao.isExcluir())
                        usuarios.remove(usuarioEdicao);
                    usuarioAdapter.notifyDataSetChanged();
                }
            }
}


