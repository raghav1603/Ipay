package in.moneytransfer.ipay.buysell.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.buysell.model.BuySell;
import in.moneytransfer.ipay.buysell.utils.BuySellConstants;

import static android.app.Activity.RESULT_OK;

public class BuySellAddFragment extends Fragment implements View.OnClickListener,OnProgressListener<UploadTask.TaskSnapshot>,
        OnPausedListener<UploadTask.TaskSnapshot>,OnSuccessListener<UploadTask.TaskSnapshot>,OnFailureListener {

    private Button addButton;
    private DatabaseReference databaseReference;
    private EditText itemName,itemPrice,itemDescription;
    private ImageView uploadImage;
    private static final int ALL_PERMISSION_RESULT = 107;
    private static final int SINGLE_SELECT_GALLERY = 105;
    private Uri uri;
    private String uriString = "uri";
    private UploadTask uploadTask;
    private StorageReference storage;
    private Activity activity;
    private String iName="a",iPrice="a",iDescription="a";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy_sell_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemName = view.findViewById(R.id.buy_sell_fragment_itemname);
        itemDescription = view.findViewById(R.id.buy_sell_fragment_description);
        itemPrice = view.findViewById(R.id.buy_sell_fragment_itemprice);
        uploadImage = view.findViewById(R.id.buy_sell_fragment_image);
        uploadImage.setOnClickListener(this);

        addButton = view.findViewById(R.id.addItem);
        addButton.setOnClickListener(this);
        activity = getActivity();

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(BuySellConstants.BUY_SELL_DATABASE_REFERENCE);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.addItem:
            {
                if (uriString.equals("uri"))
                {
                    Toast.makeText(activity, "Select Image for Product", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (itemName.getText().toString().trim().length() > 1)
                {
                    if (itemPrice.getText().toString().trim().length() > 1)
                    {
                        if (itemDescription.getText().toString().trim().length() > 1)
                        {
                            uploadImageUri(uri);
                        }
                        else
                        {
                            Toast.makeText(activity, "Enter Item Description", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else
                    {
                        Toast.makeText(activity, "Enter Item Price", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    Toast.makeText(activity, "Enter Item Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            }
            case R.id.buy_sell_fragment_image:
            {
                getPermissions();
                break;
            }
            default:
                break;
        }

    }

    private void uploadData(String imgPath)
    {
        iName = itemName.getText().toString().trim();
        iPrice = itemPrice.getText().toString().trim();
        iDescription = itemDescription.getText().toString().trim();

        String key = databaseReference.push().getKey();

        BuySell buySell = new BuySell(iName,iPrice,imgPath,iDescription,key);
        databaseReference.child(key).setValue(buySell);

        activity.getFragmentManager().popBackStack();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getPermissions()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if ((ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
            {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        ALL_PERMISSION_RESULT);
            }
            else {
                openGallery();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case ALL_PERMISSION_RESULT:
            {
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    openGallery();
                }
                else {
                    Toast.makeText(activity,"Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),SINGLE_SELECT_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SINGLE_SELECT_GALLERY && resultCode == RESULT_OK && null != data)
        {
            uri = data.getData();
            uriString = uri.toString();

        }
    }
    private void uploadImageUri(Uri uri)
    {
            storage = FirebaseStorage.getInstance().getReferenceFromUrl(BuySellConstants.BUY_SELL_STORAGE_REFERENCE);

            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("images/jpeg")
                    .build();

            uploadTask = storage.child(uri.getLastPathSegment()).putFile(uri, metadata);

            uploadTask.addOnProgressListener(this)
                    .addOnSuccessListener(this)
                    .addOnFailureListener(this)
                    .addOnPausedListener(this);

    }
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(activity, "Upload failed", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("VisibleForTests")
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        Uri downloadUri = taskSnapshot.getDownloadUrl();
        uploadData(downloadUri.toString());
    }

    @Override
    public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
        Toast.makeText(activity, "Upload paused", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("VisibleForTests")
    @Override
    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        double progress = (100.0 * (double) taskSnapshot.getBytesTransferred()) / (double) taskSnapshot.getTotalByteCount();
        Toast.makeText(activity,"Upload is "+ Math.round(progress), Toast.LENGTH_SHORT).show();
    }
}
