import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Wellbore } from 'app/shared/model/wellbore.model';
import { WellboreService } from './wellbore.service';
import { WellboreComponent } from './wellbore.component';
import { WellboreDetailComponent } from './wellbore-detail.component';
import { WellboreUpdateComponent } from './wellbore-update.component';
import { WellboreDeletePopupComponent } from './wellbore-delete-dialog.component';
import { IWellbore } from 'app/shared/model/wellbore.model';

@Injectable({ providedIn: 'root' })
export class WellboreResolve implements Resolve<IWellbore> {
    constructor(private service: WellboreService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((wellbore: HttpResponse<Wellbore>) => wellbore.body));
        }
        return of(new Wellbore());
    }
}

export const wellboreRoute: Routes = [
    {
        path: 'wellbore',
        component: WellboreComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wellbores'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wellbore/:id/view',
        component: WellboreDetailComponent,
        resolve: {
            wellbore: WellboreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wellbores'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wellbore/new',
        component: WellboreUpdateComponent,
        resolve: {
            wellbore: WellboreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wellbores'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'wellbore/:id/edit',
        component: WellboreUpdateComponent,
        resolve: {
            wellbore: WellboreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wellbores'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const wellborePopupRoute: Routes = [
    {
        path: 'wellbore/:id/delete',
        component: WellboreDeletePopupComponent,
        resolve: {
            wellbore: WellboreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Wellbores'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
