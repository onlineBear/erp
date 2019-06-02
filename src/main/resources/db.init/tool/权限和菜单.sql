begin tran

select * from tUserResource

insert into tUserResource
    (id, userId, resId, createUserId, createTime, lastUpdateTime)
select u.id + row_number() over (order by u.id), u.id, r.id, u.id, getdate(), getdate()
from tUser as u
       join tResource as r on 1=1
where not exists (select 1 from tUserResource as ur where ur.userId=u.id and ur.resId=r.id)

select * from tUserMenu

insert into tUserMenu
    (id, userId, menuId, createUserId, createTime, lastUpdateTime)
select m.id + row_number() over (order by u.id), u.id, m.id, u.id, getdate(), getdate()
from tUser as u
       join tMenu as m on 1=1
where not exists (select 1 from tUserMenu as um where um.menuId=m.id and um.userId=u.id)

commit
