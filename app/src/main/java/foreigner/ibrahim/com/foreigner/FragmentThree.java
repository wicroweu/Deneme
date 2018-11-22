package foreigner.ibrahim.com.foreigner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;


public class FragmentThree extends Fragment {

    ImageView imageView;
    Button button;
    AutoCompleteTextView story;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_three,container,false);
        imageView=view.findViewById(R.id.imageView2);
        story=view.findViewById(R.id.story);
        button=view.findViewById(R.id.button);


        return view;
    }
}
