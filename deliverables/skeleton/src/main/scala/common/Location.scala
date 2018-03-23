
abstract trait Location extends Token
with Located
with Named
with Interactive

abstract trait QuantityLimitedLocation extends Location
with Quantified

abstract trait TimeLimitedLocation extends Location
with TimeStamped
