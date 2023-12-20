package day20

data class Sample(val input: String, val answer: Long)

val inputs1 = listOf(
    Sample("""
broadcaster -> a, b, c
%a -> b
%b -> c
%c -> inv
&inv -> a
""".trimIndent(), 32000000),
    Sample("""
broadcaster -> a
%a -> inv, con
&inv -> b
%b -> con
&con -> output
""".trimIndent(), 11687500)
)

val inputs2 = listOf<Sample>(
)

val inputMine = """
%zf -> rl
%gb -> cx, nn
%pm -> ff
%jf -> nn, zd
%zr -> rl, cf
%jb -> bh
%kp -> rd
%lq -> sm
%sd -> rd, vg
%vm -> jb
%sx -> rl, zg
%dh -> sz, qb
&lx -> rx
%sz -> qb
%dt -> np, rd
&qb -> bf, kf, hd, nl, pm, lb
%tk -> zr
&nn -> nj, gs, vr
%ph -> dj, rd
&rl -> tk, lq, zg, vm, jb, sx, cl
%bf -> kf
%hd -> ms, qb
%lf -> mg, nn
%cx -> nn, lf
%bh -> rl, tk
%zm -> rd, kp
%vr -> jf
%cf -> zf, rl
%kf -> zc
%bz -> gd, nn
%np -> hq
%mg -> nn
&cl -> lx
%cc -> qb, nl
%cb -> nk, nn
%fc -> rd, dt
&rp -> lx
&lb -> lx
%zg -> sr
&nj -> lx
%zc -> qb, pm
%zd -> bz, nn
%dj -> zm, rd
&rd -> np, rp, fc
broadcaster -> hd, gs, fc, sx
%gs -> vr, nn
%gd -> cb, nn
%ck -> sd, rd
%rk -> ck, rd
%ff -> qb, lm
%ms -> qb, cc
%vg -> ph, rd
%sr -> lq, rl
%hq -> rd, rk
%nl -> bf
%nk -> gb, nn
%sm -> vm, rl
%lm -> dh, qb
""".trimIndent()
