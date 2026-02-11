package pl.luki2183.farmManager.utils;

public interface Finder<T, ID extends String> {
    T find(ID id);
}
