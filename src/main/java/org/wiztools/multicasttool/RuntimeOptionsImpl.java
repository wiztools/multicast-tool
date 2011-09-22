package org.wiztools.multicasttool;

/**
 * Bean holding runtime options.
 * @author subwiz
 */
class RuntimeOptionsImpl implements RuntimeOptions {
    private boolean verbose = false; // default is false

    @Override
    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
