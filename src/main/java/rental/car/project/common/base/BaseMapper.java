package rental.car.project.common.base;

public interface BaseMapper <E, DTO> {

    public DTO convertToDto(E entity);

    public E convertToEntity(DTO dto);
}
