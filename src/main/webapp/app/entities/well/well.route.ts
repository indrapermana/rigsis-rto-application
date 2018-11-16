import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Well } from 'app/shared/model/well.model';
import { WellService } from './well.service';
import { WellComponent } from './well.component';
import { WellDetailComponent } from './well-detail.component';
import { WellUpdateComponent } from './well-update.component';
import { WellDeletePopupComponent } from './well-delete-dialog.component';
import { IWell } from 'app/shared/model/well.model';

@Injectable({ providedIn: 'root' })
export class WellResolve implements Resolve<IWell> {
    constructor(private service: WellService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((well: HttpResponse<Well>) => well.body));
        }
        return of(new Well());
    }
}

export const wellRoute: Routes = [
    {
        path: 'well',
        component: WellComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wells'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'well/:id/view',
        component: WellDetailComponent,
        resolve: {
            well: WellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wells'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'well/new',
        component: WellUpdateComponent,
        resolve: {
            well: WellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wells'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'well/:id/edit',
        component: WellUpdateComponent,
        resolve: {
            well: WellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wells'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wellPopupRoute: Routes = [
    {
        path: 'well/:id/delete',
        component: WellDeletePopupComponent,
        resolve: {
            well: WellResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wells'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
