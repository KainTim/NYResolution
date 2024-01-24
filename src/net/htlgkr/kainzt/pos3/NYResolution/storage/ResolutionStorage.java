package net.htlgkr.kainzt.pos3.NYResolution.storage;

import net.htlgkr.kainzt.pos3.NYResolution.Resolution;

import java.util.ArrayList;
import java.util.List;

public class ResolutionStorage {
    public void setResolutions(List<Resolution> resolutions) {
        this.resolutions = resolutions;
    }

    private List<Resolution> resolutions;
    public ResolutionStorage() {
        resolutions = new ArrayList<>();
    }
    public void addResolution(Resolution resolution){
        resolutions.add(resolution);
    }
    public List<Resolution> getResolutions() {
        return resolutions;
    }

    public void deleteResolution(Resolution resolution) {
        resolutions.remove(resolution);
    }
}
