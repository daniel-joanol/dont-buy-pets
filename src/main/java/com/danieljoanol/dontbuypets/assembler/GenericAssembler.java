package com.danieljoanol.dontbuypets.assembler;

import java.util.ArrayList;
import java.util.List;

import com.danieljoanol.dontbuypets.dto.GenericDTO;

public class GenericAssembler<T, U extends GenericDTO> {

    public U convertToDTO(T container) {
        return null;
    }

    public List<U> convertToDTO(List<T> containers) {
        List<U> res = new ArrayList<U>();
        for (T container : containers) {
            res.add(convertToDTO(container));
        }
        return res;
    }
    
    public T convertFromDTO(U containerVO) {
        return (T) containerVO.toEntity();
    }

    public List<T> convertFromDTO(List<U> containers) {
        List<T> res = new ArrayList<T>();
        for (U container : containers) {
            res.add(convertFromDTO(container));
        }
        return res;
    }

}
