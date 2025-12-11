package rental.car.project.utils.base;

public interface BaseMapper <E, DTO> {

    public DTO convertToDto(E entity);

    public E convertToEntity(DTO dto);
}
