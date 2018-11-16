import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IWell } from 'app/shared/model/well.model';
import { Principal } from 'app/core';
import { WellService } from './well.service';

@Component({
    selector: 'jhi-well',
    templateUrl: './well.component.html'
})
export class WellComponent implements OnInit, OnDestroy {
    wells: IWell[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private wellService: WellService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.wellService.query().subscribe(
            (res: HttpResponse<IWell[]>) => {
                this.wells = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInWells();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IWell) {
        return item.id;
    }

    registerChangeInWells() {
        this.eventSubscriber = this.eventManager.subscribe('wellListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
