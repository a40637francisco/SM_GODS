function out = binary2vector(data,nBits)
    if(nargin == 1)
        res = de2bi(data);
    else
        res = de2bi(data,nBits);
    end
    res = fliplr(res);
    out = res;
end

