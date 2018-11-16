import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IWellbore } from 'app/shared/model/wellbore.model';
import { Principal } from 'app/core';
import { WellboreService } from './wellbore.service';

@Component({
    selector: 'jhi-wellbore',
    templateUrl: './wellbore.component.html'
})
export class WellboreComponent implements OnInit, OnDestroy {
    wellbores: IWellbore[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private wellboreService: WellboreService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.wellboreService.query().subscribe(
            (res: HttpResponse<IWellbore[]>) => {
                this.wellbores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInWellbores();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IWellbore) {
        return item.id;
    }

    registerChangeInWellbores() {
        this.eventSubscriber = this.eventManager.subscribe('wellboreListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
