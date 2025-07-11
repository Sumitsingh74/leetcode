class Solution:
        def minMoves(self, sx: int, sy: int, tx: int, ty: int) -> int:
            if sx == 0 and sy == 0:
                return 0 if tx == ty == 0 else -1
            res = 0
            while (sx, sy) != (tx, ty):
                if sx > tx or sy > ty:
                    return -1
                res += 1
                if tx > ty:
                    if tx > ty * 2:
                        if tx % 2:
                            return -1
                        tx >>= 1
                    else:
                        tx -= ty
                elif tx < ty:
                    if ty > tx * 2:
                        if ty % 2:
                            return -1
                        ty >>= 1
                    else:
                        ty -= tx
                else:
                    if sx == 0:
                        tx = 0
                    elif sy == 0:
                        ty = 0
                    else:
                        return -1

            return res if (sx, sy) == (tx, ty) else -1