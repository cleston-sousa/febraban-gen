package br.net.gits.febraban.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperUtils {

	private static ModelMapper modelMapper = new ModelMapper();

	public static <E, R> R to(E objectSource, Class<R> responseClazz) {
		return modelMapper.map(objectSource, responseClazz);
	}

	public static <E, R> List<R> toList(Collection<E> listSource, Class<R> clazz) {
		return listSource.stream().map(item -> to(item, clazz)).collect(Collectors.toList());
	}

	public static void set(Object request, Object target) {
		modelMapper.map(request, target);
	}

}
