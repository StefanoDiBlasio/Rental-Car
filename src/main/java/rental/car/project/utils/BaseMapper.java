package rental.car.project.utils;

public interface BaseMapper <E, DTO> {

    public DTO convertToDto(E entity);

    public E convertToEntity(DTO dto);
}
