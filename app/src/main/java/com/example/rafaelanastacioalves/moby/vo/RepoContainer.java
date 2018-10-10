package com.example.rafaelanastacioalves.moby.vo;

import java.util.List;

public class RepoContainer {
    private final List<Repo> items;

    public RepoContainer(List<Repo> listRepo) {
        this.items = listRepo;
    }

    public List<Repo> getRepoList() {
        return items;
    }
}

