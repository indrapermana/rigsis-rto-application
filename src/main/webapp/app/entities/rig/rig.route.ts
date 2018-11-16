import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Rig } from 'app/shared/model/rig.model';
import { RigService } from './rig.service';
import { RigComponent } from './rig.component';
import { RigDetailComponent } from './rig-detail.component';
import { RigUpdateComponent } from './rig-update.component';
import { RigDeletePopupComponent } from './rig-delete-dialog.component';
import { IRig } from 'app/shared/model/rig.model';

@Injectable({ providedIn: 'root' })
export class RigResolve implements Resolve<IRig> {
    constructor(private service: RigService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((rig: HttpResponse<Rig>) => rig.body));
        }
        return of(new Rig());
    }
}

export const rigRoute: Routes = [
    {
        path: 'rig',
        component: RigComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rigs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rig/:id/view',
        component: RigDetailComponent,
        resolve: {
            rig: RigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rigs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rig/new',
        component: RigUpdateComponent,
        resolve: {
            rig: RigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rigs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rig/:id/edit',
        component: RigUpdateComponent,
        resolve: {
            rig: RigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rigs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rigPopupRoute: Routes = [
    {
        path: 'rig/:id/delete',
        component: RigDeletePopupComponent,
        resolve: {
            rig: RigResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rigs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
