export function formatParameters(parameterCount) {
    if (parameterCount >= 1_000_000_000) {
        return (parameterCount / 1_000_000_000).toFixed(1).replace(/\.0$/, '') + 'B';
    } else if (parameterCount >= 1_000_000) {
        return (parameterCount / 1_000_000).toFixed(1).replace(/\.0$/, '') + 'M';
    } else if (parameterCount >= 1_000) {
        return (parameterCount / 1_000).toFixed(1).replace(/\.0$/, '') + 'K';
    } else {
        return parameterCount.toString();
    }        
}