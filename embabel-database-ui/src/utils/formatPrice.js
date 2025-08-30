

export function formatPrice(cents) {
  // Explicitly check for nullish or non-number values
  if (cents === null || cents === undefined || Number.isNaN(cents)) {
    console.warn("price is empty... returning");
    return "";
  }

  // Make sure it's a number
  if (typeof cents !== "number") {
    console.warn("Invalid price type... returning");
    return "";
  }
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "USD",
    minimumFractionDigits: 2
  }).format(cents / 100);
};