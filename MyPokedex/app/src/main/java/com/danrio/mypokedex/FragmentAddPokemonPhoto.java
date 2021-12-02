package com.danrio.mypokedex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.io.FileDescriptor;

public class FragmentAddPokemonPhoto extends Fragment {
    private final JavaMethods jm = new JavaMethods();
    private Button btnChangeRegular, btnChangeShiny;
    private ImageView imgRegular, imgShiny;
    private SharedViewModelPokemon viewModelPokemon;
    private final ActivityResultLauncher<String> pickShinyPhotoLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        getImgShiny().setImageURI(uri);
        try {
            ParcelFileDescriptor parcelFileDescriptor = requireContext().getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            setShinyPhotoVMP(image);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });
    private final ActivityResultLauncher<Intent> takeShinyPhotoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent intent = result.getData();
            try {
                Bitmap bitmap = (Bitmap) (intent != null ? intent.getExtras().get("data") : null);
                setShinyPhotoVMP(bitmap);
                getImgShiny().setImageBitmap(bitmap);
            } catch (Exception ex) {
                Snackbar.make(requireView(), R.string.error_occurred, Snackbar.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }
    });
    private final ActivityResultLauncher<String> requestShinyPhoto = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            takeShinyPhoto();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(R.string.permission_needed_title).setIcon(R.drawable.info_icon).setMessage(R.string.camera_permission_needed_message);
            builder.setPositiveButton(R.string.open_settings, (dialog, which) -> JavaMethods.startInstalledAppDetailsActivity(requireActivity()));
            builder.setNeutralButton(R.string.no_thanks, null).create().show();
        }
    });
    private final ActivityResultLauncher<Intent> takeRegularPhotoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent intent = result.getData();
            try {
                Bitmap bitmap = (Bitmap) (intent != null ? intent.getExtras().get("data") : null);
                setRegularPhotoVMP(bitmap);
                getImgRegular().setImageBitmap(bitmap);
            } catch (Exception ex) {
                Snackbar.make(requireView(), R.string.error_occurred, Snackbar.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }
    });
    private final ActivityResultLauncher<String> requestRegularPhoto = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            takeRegularPhoto();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle(R.string.permission_needed_title).setIcon(R.drawable.info_icon).setMessage(R.string.camera_permission_needed_message);
            builder.setPositiveButton(R.string.open_settings, (dialog, which) -> JavaMethods.startInstalledAppDetailsActivity(requireActivity()));
            builder.setNeutralButton(R.string.no_thanks, null).create().show();
        }
    });
    private final ActivityResultLauncher<String> pickRegularPhotoLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        getImgRegular().setImageURI(uri);
        try {
            ParcelFileDescriptor parcelFileDescriptor = requireContext().getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            setRegularPhotoVMP(image);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });

    public FragmentAddPokemonPhoto() {
        // Required empty public constructor
    }

    public SharedViewModelPokemon getViewModelPokemon() {
        return viewModelPokemon;
    }

    public void setViewModelPokemon(SharedViewModelPokemon viewModelPokemon) {
        this.viewModelPokemon = viewModelPokemon;
    }

    public JavaMethods getJm() {
        return jm;
    }

    public ActivityResultLauncher<String> getRequestShinyPhoto() {
        return requestShinyPhoto;
    }

    public ActivityResultLauncher<String> getRequestRegularPhoto() {
        return requestRegularPhoto;
    }

    public ActivityResultLauncher<String> getPickRegularPhotoLauncher() {
        return pickRegularPhotoLauncher;
    }

    public ActivityResultLauncher<Intent> getTakeRegularPhotoLauncher() {
        return takeRegularPhotoLauncher;
    }

    public ActivityResultLauncher<String> getPickShinyPhotoLauncher() {
        return pickShinyPhotoLauncher;
    }

    public ActivityResultLauncher<Intent> getTakeShinyPhotoLauncher() {
        return takeShinyPhotoLauncher;
    }

    public Button getBtnChangeRegular() {
        return btnChangeRegular;
    }

    public void setBtnChangeRegular(Button btnChangeRegular) {
        this.btnChangeRegular = btnChangeRegular;
    }

    public Button getBtnChangeShiny() {
        return btnChangeShiny;
    }

    public void setBtnChangeShiny(Button btnChangeShiny) {
        this.btnChangeShiny = btnChangeShiny;
    }

    public ImageView getImgRegular() {
        return imgRegular;
    }

    public void setImgRegular(ImageView imgRegular) {
        this.imgRegular = imgRegular;
    }

    public ImageView getImgShiny() {
        return imgShiny;
    }

    public void setImgShiny(ImageView imgShiny) {
        this.imgShiny = imgShiny;
    }

    private void setRegularPhotoVMP(Bitmap bitmap) {
        try {
            byte[] byteArray = getJm().getByteArrayFromBitmap(bitmap);
            getViewModelPokemon().setRegular_photo(byteArray);
            System.out.println("Regular photo length: " + getViewModelPokemon().getPokemonClass().getRegular_photo().length);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setShinyPhotoVMP(Bitmap bitmap) {
        try {
            byte[] byteArray = getJm().getByteArrayFromBitmap(bitmap);
            getViewModelPokemon().setShiny_photo(byteArray);
            System.out.println("Shiny photo length: " + getViewModelPokemon().getPokemonClass().getShiny_photo().length);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModelPokemon(new ViewModelProvider(requireActivity()).get(SharedViewModelPokemon.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_pokemon_photo, container, false);
        setBtnChangeRegular(v.findViewById(R.id.btnChangeRegularPhoto));
        setBtnChangeShiny(v.findViewById(R.id.btnChangeShinyPhoto));
        setImgRegular(v.findViewById(R.id.imgRegular));
        setImgShiny(v.findViewById(R.id.imgShiny));
        getBtnChangeRegular().setOnClickListener(v1 -> getJm().changeRegularPhoto(requireContext(), requireView(), getRequestRegularPhoto(), getPickRegularPhotoLauncher()));
        getBtnChangeShiny().setOnClickListener(v1 -> getJm().changeShinyPhoto(requireContext(), requireView(), getRequestShinyPhoto(), getPickShinyPhotoLauncher()));
        if (savedInstanceState != null) {
            try {
                if (getViewModelPokemon().getPokemonClass().getRegular_photo() != null) {
                    getImgRegular().setImageBitmap(getJm().getBitmapFromByteArray(getViewModelPokemon().getPokemonClass().getRegular_photo()));
                }
                if (getViewModelPokemon().getPokemonClass().getShiny_photo() != null) {
                    getImgShiny().setImageBitmap(getJm().getBitmapFromByteArray(getViewModelPokemon().getPokemonClass().getShiny_photo()));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        getImgRegular().setOnLongClickListener(v1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.EditAlertDialog);
            builder.setIcon(R.drawable.delete_icon).setTitle(R.string.activity_Pokemon_removeRegularPhoto);
            builder.setMessage(R.string.activity_Pokemon_removePhotoWarning);
            builder.setNeutralButton(R.string.no, null).setPositiveButton(R.string.yes, (dialog, which) -> removeRegularPhoto()).create().show();
            return true;
        });
        getImgShiny().setOnLongClickListener(v1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.EditAlertDialog);
            builder.setIcon(R.drawable.delete_icon).setTitle(R.string.activity_Pokemon_removeShinyPhoto);
            builder.setMessage(R.string.activity_Pokemon_removePhotoWarning);
            builder.setNeutralButton(R.string.no, null).setPositiveButton(R.string.yes, (dialog, which) -> removeShinyPhoto()).create().show();
            return true;
        });
        return v;
    }

    private void removeRegularPhoto() {
        getImgRegular().setImageURI(null);
        getImgRegular().setImageResource(R.drawable.photo_icon);
        getViewModelPokemon().setRegular_photo(null);
    }

    private void removeShinyPhoto() {
        getImgShiny().setImageURI(null);
        getImgShiny().setImageResource(R.drawable.photo_icon);
        getViewModelPokemon().setShiny_photo(null);
    }

    private void takeRegularPhoto() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            getTakeRegularPhotoLauncher().launch(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } catch (Exception ex) {
            Snackbar.make(this.requireView(), R.string.error_occurred, Snackbar.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    private void takeShinyPhoto() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            getTakeShinyPhotoLauncher().launch(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } catch (Exception ex) {
            Snackbar.make(this.requireView(), R.string.error_occurred, Snackbar.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}