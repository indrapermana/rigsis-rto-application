/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RtoAppTestModule } from '../../../test.module';
import { DerivedUnitDeleteDialogComponent } from 'app/entities/derived-unit/derived-unit-delete-dialog.component';
import { DerivedUnitService } from 'app/entities/derived-unit/derived-unit.service';

describe('Component Tests', () => {
    describe('DerivedUnit Management Delete Component', () => {
        let comp: DerivedUnitDeleteDialogComponent;
        let fixture: ComponentFixture<DerivedUnitDeleteDialogComponent>;
        let service: DerivedUnitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [DerivedUnitDeleteDialogComponent]
            })
                .overrideTemplate(DerivedUnitDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DerivedUnitDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DerivedUnitService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
