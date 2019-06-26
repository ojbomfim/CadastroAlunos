package unipac.com.br.cadastrodealunos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    private ArrayList<Usuario> usuarios;
    private EditText home_search;

    public UsuarioAdapter(@NonNull Context context, @NonNull ArrayList<Usuario> usuarios) {
        super(context, 0,usuarios);
        this.usuarios = usuarios;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Usuario usuario = usuarios.get(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_usuario,null);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        TextView TxtNome = (TextView)convertView.findViewById(R.id.TxtNome);
        TextView TxtEmail = (TextView)convertView.findViewById(R.id.TxtEmail);
        TextView TxtTelefone = (TextView)convertView.findViewById(R.id.TxtTelefone);

        TxtNome.setText(usuario.getNome().toString());
        TxtEmail.setText(usuario.getEmail().toString());
        TxtTelefone.setText(usuario.getTelefone().toString());
        if (usuario.getAvatar() != null)
            imageView.setImageBitmap(usuario.getAvatar());

        return  convertView;

        }

}
