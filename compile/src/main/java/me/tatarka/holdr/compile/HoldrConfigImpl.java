package me.tatarka.holdr.compile;

import me.tatarka.holdr.model.HoldrConfig;

import java.io.Serializable;

/**
 * User: evantatarka
 * Date: 10/2/14
 * Time: 5:21 PM
 */
public class HoldrConfigImpl implements HoldrConfig, Serializable {
    private final String manifestPackage;
    private final String holdrPackage;
    private final boolean defaultInclude;

    public HoldrConfigImpl(String manifestPackage, String holdrPackage, boolean defaultInclude) {
        this.manifestPackage = manifestPackage;
        this.holdrPackage = holdrPackage;
        this.defaultInclude = defaultInclude;
    }

    @Override
    public String getManifestPackage() {
        return manifestPackage;
    }

    @Override
    public String getHoldrPackage() {
        return holdrPackage;
    }

    @Override
    public boolean getDefaultInclude() {
        return defaultInclude;
    }
}
