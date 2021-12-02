package com.danrio.mypokedex;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPokemonPagerAdapter extends FragmentStateAdapter {
    private String id;

    public ViewPokemonPagerAdapter(@NonNull FragmentActivity fragmentActivity, String id) {
        super(fragmentActivity);
        setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return FragmentViewPokemonMove.newInstance(getId());
            case 2:
                return FragmentViewPokemonEvolution.newInstance(getId());
            case 3:
                return FragmentViewPokemonForm.newInstance(getId());
            default:
                return FragmentViewPokemonInfo.newInstance(getId());
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
