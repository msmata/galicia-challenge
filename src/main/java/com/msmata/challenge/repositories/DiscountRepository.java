public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByCategory(String category);
}
