# operator-filter

Filters values

## Inputs

* value (any): Any value triggers a notification. The value can be used in the notification.

## Outputs

* value (any): Same value as input, if filter is matched.

## Configs

* filter (any): value is compared against this filter. The filter rule used depends on the environment variable FILTER_TYPE. Use the image tagged with desired filter.

## Filter types
| Filter Type | Example Value | Tag      |
|-------------|---------------|----------|
| all         | a             | all      |
| equal       | a             | equal    |
| interval    | [-3,8)        | interval |
| unequal     | a             | unequal  |
| older-than  | 1h            | unequal  |
