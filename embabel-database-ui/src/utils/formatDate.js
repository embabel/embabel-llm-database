export function formatDate(isoString) {
  //validate
  if (!isoString || typeof isoString !== "string") {
    console.warn("date is empty... returning")
    return "";
  }
  let sanitized = isoString.replace(/"/g,'').replace(/\.(\d{3})\d*/, '.$1');

  const date = new Date(sanitized);

  // Extract parts
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');

  // Return in desired format: yyyy-mm-dd hh:mm
  return `${year}-${month}-${day} ${hours}:${minutes}`;
}