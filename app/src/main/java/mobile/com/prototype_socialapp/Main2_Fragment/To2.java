package mobile.com.prototype_socialapp.Main2_Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import mobile.com.prototype_socialapp.Join;
import mobile.com.prototype_socialapp.MainFragment.Main2;
import mobile.com.prototype_socialapp.R;

/**
 * Created by Daehwan Kim on 2017-06-11.
 */

public class To2 extends Fragment {

    View view;
    Boolean SelectToner = false;
    int TonerPosition;
    String[] TonerList = {"4강", "8강"};

    To3_4 to3_4;
    To3_8 to3_8;

    public static TextView Win1Prize;
    public static TextView Win2Prize;
    public static TextView JoinPrice;

    public static To2 newInstance(){
        To2 to1 = new To2();
        return to1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toner2, container, false);

        final TextView Toner = (TextView) view.findViewById(R.id.To2_SelectToner);
        Win1Prize = (TextView) view.findViewById(R.id.To2_Win1Prize);
        Win2Prize = (TextView) view.findViewById(R.id.To2_Win2Prize);
        JoinPrice = (TextView) view.findViewById(R.id.To2_JoinPrice);
        TextView OKButton = (TextView) view.findViewById(R.id.To2_OKButton);

        to3_4 = To3_4.newInstance();
        to3_8 = To3_8.newInstance();

        Toner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, TonerList);

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_slect_local, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialogView);

                ListView LocalListView = (ListView) dialogView.findViewById(R.id.Dialog_LocalListView);
                LocalListView.setAdapter(adapter);

                final AlertDialog dialog = builder.create();
                dialog.show();

                LocalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toner.setText(TonerList[position]);
                        SelectToner = true;
                        TonerPosition = position;
                        dialog.dismiss();
                    }
                });
            }
        });

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectToner){
                    if (Win1Prize.getText().toString().length() > 0){
                        if (Win2Prize.getText().toString().length() > 0){
                            if (JoinPrice.getText().toString().length() > 0){
                                if (TonerPosition==0){
                                    Main2.fm.beginTransaction().replace(R.id.Main2_Fragement, to3_4).commit();
                                }else{
                                    Main2.fm.beginTransaction().replace(R.id.Main2_Fragement, to3_8).commit();
                                }
                            }else{
                                Toast.makeText(getActivity(), "참가비를 입력해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getActivity(), "준우승금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "최종우승금액을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "종류를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public static String toNumFormat(int num) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }

}
